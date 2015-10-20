package com.kryptnostic.directory.v1.domain;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.constants.Names;

public class Domain {
    public static final String        PUBLICLY_LISTABLE_FIELD         = "publiclyListable";
    private static final String        DOMAIN_SHARING_POLICY_FIELD     = "domainSharingPolicy";
    private static final String        OPEN_REGISTRATION_ENABLED_FIELD = "openRegistrationEnabled";

    public static final boolean       CONFIRMATION_EMAIL_NOT_REQUIRED = false;
    public static final boolean       CONFIRMATION_EMAIL_REQUIRED     = true;
    public static final boolean       NOT_PUBLICLY_LISTABLE           = true;
    public static final boolean       PUBLICLY_LISTABLE               = true;
    public static final boolean       OPEN_REGISTRATION_ENABLED       = true;

    private final UUID                id;
    private final String              name;
    private final AtomicInteger       size;                                                   // Total number of users
                                                                                               // in
                                                                                               // domain
    private final DomainSharingPolicy domainSharingPolicy;
    private final boolean             confirmationEmailRequired;
    private final AtomicBoolean       publiclyListable;

    @JsonCreator
    public Domain(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.DOMAIN_FIELD ) String name,
            @JsonProperty( Names.SIZE_FIELD ) int size,
            @JsonProperty( DOMAIN_SHARING_POLICY_FIELD ) DomainSharingPolicy domainSharingPolicy,
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailRequired,
            @JsonProperty( PUBLICLY_LISTABLE_FIELD ) Optional<Boolean> publiclyListable ) {
        this.id = id;
        this.name = name;
        this.size = new AtomicInteger( size );
        this.domainSharingPolicy = domainSharingPolicy;
        this.confirmationEmailRequired = confirmationEmailRequired.or( CONFIRMATION_EMAIL_NOT_REQUIRED );
        this.publiclyListable = new AtomicBoolean( publiclyListable.or( PUBLICLY_LISTABLE ) );
    }

    public Domain( UUID id, String name, int size ) {
        this( id, name, size, DomainSharingPolicy.AllDomains, Optional.of( CONFIRMATION_EMAIL_NOT_REQUIRED ), Optional
                .of( PUBLICLY_LISTABLE ) );
    }

    public Domain( DomainBuilder domainBuilder ) {
        this.id = domainBuilder.id;
        this.name = domainBuilder.name;
        this.size = domainBuilder.size;
        this.domainSharingPolicy = domainBuilder.domainSharingPolicy;
        this.confirmationEmailRequired = domainBuilder.confirmationEmailRequired;
        this.publiclyListable = domainBuilder.publiclyListable;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @JsonProperty( Names.DOMAIN_FIELD )
    public String getName() {
        return name;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size.get();
    }

    @JsonProperty( DOMAIN_SHARING_POLICY_FIELD )
    public DomainSharingPolicy getDomainSharingPolicy() {
        return domainSharingPolicy;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailRequired() {
        return confirmationEmailRequired;
    }

    @JsonProperty( PUBLICLY_LISTABLE_FIELD )
    public boolean isPubliclyListable() {
        return publiclyListable.get();
    }

    @JsonProperty( OPEN_REGISTRATION_ENABLED_FIELD )
    public boolean isOpenRegistrationEnabled() {
        return publiclyListable.get();
    }

    @JsonIgnore
    public Domain incrementSize() {
        size.incrementAndGet();
        return this;
    }

    @JsonIgnore
    public Domain makePubliclyListable() {
        publiclyListable.set( true );
        return this;
    }

    @JsonIgnore
    public Domain makePrivate() {
        publiclyListable.set( false );
        return this;
    }

    @Override
    public String toString() {
        return "Domain [id=" + id + ", name=" + name + ", size=" + size + ", domainSharingPolicy="
                + domainSharingPolicy.toString() + ", confirmationEmailRequired=" + confirmationEmailRequired + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( confirmationEmailRequired ? 1231 : 1237 );
        result = prime * result + ( ( domainSharingPolicy == null ) ? 0 : domainSharingPolicy.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( publiclyListable == null ) ? 0 : publiclyListable.hashCode() );
        result = prime * result + ( ( size == null ) ? 0 : size.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof Domain ) ) {
            return false;
        }
        Domain other = (Domain) obj;
        if ( confirmationEmailRequired != other.confirmationEmailRequired ) {
            return false;
        }
        if ( domainSharingPolicy != other.domainSharingPolicy ) {
            return false;
        }
        if ( id == null ) {
            if ( other.id != null ) {
                return false;
            }
        } else if ( !id.equals( other.id ) ) {
            return false;
        }
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        } else if ( !name.equals( other.name ) ) {
            return false;
        }
        synchronized ( publiclyListable ) {
            synchronized ( other.publiclyListable ) {
                if ( publiclyListable == null ) {
                    if ( other.publiclyListable != null ) {
                        return false;
                    }
                } else if ( publiclyListable.get() != other.publiclyListable.get() ) {
                    return false;
                }
            }
        }
        synchronized ( size ) {
            synchronized ( other.size ) {
                if ( size == null ) {
                    if ( other.size != null ) {
                        return false;
                    }
                } else if ( size.get() != other.size.get() ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class DomainBuilder {
        private UUID                id;
        private String              name;
        private AtomicInteger       size;
        private DomainSharingPolicy domainSharingPolicy;
        private boolean             confirmationEmailRequired;
        private AtomicBoolean       publiclyListable;
        private boolean             openRegistrationRequired;

        public DomainBuilder( UUID id, String name ) {
            this.id = id;
            this.name = name;
        }

        public DomainBuilder withSize( Integer size ) {
            this.size = new AtomicInteger( size );
            return this;
        }

        public DomainBuilder withSharingPolicy( DomainSharingPolicy sharingPolicy ) {
            this.domainSharingPolicy = sharingPolicy;
            return this;
        }

        public DomainBuilder emailConfirmationRequiredIs( boolean isRequired ) {
            this.confirmationEmailRequired = isRequired;
            return this;
        }

        public DomainBuilder openRegistrationEnabled( boolean openReg ) {
            this.openRegistrationRequired = openReg;
            return this;
        }

        public DomainBuilder publiclyListableIs( Boolean publiclyListable ) {
            this.publiclyListable = new AtomicBoolean( publiclyListable );
            return this;
        }

        public Domain build() {
            Preconditions.checkNotNull( this.size );
            Preconditions.checkNotNull( this.domainSharingPolicy );
            Preconditions.checkNotNull( this.confirmationEmailRequired );
            Preconditions.checkNotNull( this.publiclyListable );
            return new Domain( this );
        }

    }
}
