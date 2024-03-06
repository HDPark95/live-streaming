package project.livestreaming.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@DynamicInsert
public abstract class BaseEntity {

    //등록일
    @Column(name = "created_date", insertable = true, nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    //최종 수정일
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private boolean deleted = false;

    // 등록자
    @JsonIgnore
    @CreatedBy
    @JoinColumn(name="created_by", insertable=true, updatable = false)
    private Long createdBy;

    // 수정자
    @JsonIgnore
    @LastModifiedBy
    @JoinColumn(name="modified_by")
    private Long modifiedBy;

}