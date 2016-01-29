package com.kryptnostic.v2.search;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.storage.models.VersionedObjectKey;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SearchApi {
    String CONTROLLER              = "/search";
    String OBJECT_ID               = "objectId";
    String OBJECT_ID_PATH          = "/{" + OBJECT_ID + "}";
    String COUNT                   = "count";
    String COUNT_PATH              = "/{" + COUNT + "}";

    /**
     * The values of the returned map are lists of index segment ids;
     * WARNING: the list of UUIDs are NOT object ids corresponding to
     * actual documents containing the search term!
     *
     * The keys correspond to the actual objects whose cryptoservices
     * should be used to decrypt the index segments after loading them
     *
     * To obtain the actual documents containing the search terms, the client
     * needs to:
     * (1) Load and decrypt the objects specified in the keys
     *     to obtain inverted index segments
     * (2) Filter out those inverted index segments corresponding to terms that
     *     did not appear in the original set of search terms (this filtering
     *     is necessary due to potential hash collisions)
     * (3) Collect the object ids appear in the remaining inverted index
     *     segments; loading and decrypting these objects will result in the
     *     actual documents containing the search terms
     */
    @POST( CONTROLLER )
    Map<VersionedObjectKey, Set<UUID>> search( @Body List<byte[]> fheEncryptedSearchTerms );

    /**
     * For a given inverted index segment corresponding to a particular object id
     * and search term, we compute the "address" at which to store the inverted
     * index segment as follows:
     * (1) Obtain an object search pair of the nearest ancestor that has an object
     *     search pair (in the case of kodex, this would be the channel)
     * (2) Use the client hash function, object search pair, and fhe encrypted search
     *     term to compute the "base address"
     * (3) Compute SHA256( base_address || j ), where j is the smallest natural number
     *     that has not been used in calculating addresses for the ancestor object
     *     from step (1)
     *
     * The result of step (3) is the "address" at which we store the inverted
     * index segment.
     *
     * The motivation for using distinct values of j is to avoid having multiple
     * inverted index segments corresponding to a particular search term getting
     * stored at the same address, because we want to avoid leaking information
     * about the distribution of term frequencies.
     *
     * The client and server must cooperate to prevent reusing values of j.  The
     * current segment count is the smallest value of j that's safe to use;
     * calling getAndAddSegmentCount with count = N will cause the server to
     * "reserve" the next N values of j and then return the current count.
     *
     * Note that the client shuffles its inverted index segments before computing
     * addresses, to partially protect against the case of a malicious server that
     * fails to increase its stored segment counts (and e.g. returns 0 every time
     * getAndAddSegmentCount is called, no matter what count the client passes in)
     */
    @POST( CONTROLLER + OBJECT_ID_PATH + COUNT_PATH )
    public int getAndAddSegmentCount( @Path( OBJECT_ID ) UUID objectId, @Path( COUNT ) int count );
}
