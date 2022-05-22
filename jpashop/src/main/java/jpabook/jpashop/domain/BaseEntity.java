package jpabook.jpashop.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private String createBy;

    private LocalDateTime createDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;

}
