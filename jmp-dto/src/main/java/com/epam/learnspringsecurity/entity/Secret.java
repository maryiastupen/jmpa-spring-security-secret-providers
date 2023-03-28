package com.epam.learnspringsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * Entity, that represents secret sent by a user
 */
@Entity
@Table(name = "secret")
public class Secret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secret_id")
    private long id;

    @Column(name = "unique_address_identifier")
    private String uniqueAddressIdentifier;

    @Column(name = "secret_value")
    private String secretValue;

    public Secret(String uniqueAddressIdentifier, String secretValue) {
        this.uniqueAddressIdentifier = uniqueAddressIdentifier;
        this.secretValue = secretValue;
    }

    public Secret(long id, String uniqueAddressIdentifier, String secretValue) {
        this.id = id;
        this.uniqueAddressIdentifier = uniqueAddressIdentifier;
        this.secretValue = secretValue;
    }

    public Secret() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueAddressIdentifier() {
        return uniqueAddressIdentifier;
    }

    public void setUniqueAddressIdentifier(String uniqueAddressIdentifier) {
        this.uniqueAddressIdentifier = uniqueAddressIdentifier;
    }

    public String getSecretValue() {
        return secretValue;
    }

    public void setSecretValue(String secretValue) {
        this.secretValue = secretValue;
    }

}
