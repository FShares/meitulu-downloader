package club.geek66.downloader.meitulu.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 * @author: 橙子
 * @date: 2019/11/7
 * @time: 22:04
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@CreatedBy
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;*/

	@CreatedDate
	private ZonedDateTime createdAt;

	@LastModifiedDate
	private ZonedDateTime modifiedAt;

}
