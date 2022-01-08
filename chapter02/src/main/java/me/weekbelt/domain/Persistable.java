package me.weekbelt.domain;

import java.time.Instant;

public interface Persistable {

    Integer getId();

    void setId(Integer Id);

    Instant getCreateTimestamp();

    void setCreateTimestamp(Instant instant);

}
