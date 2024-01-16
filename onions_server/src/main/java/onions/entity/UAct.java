package onions.entity;

import lombok.Data;
import javax.persistence.*;;
import java.io.Serializable;

/**
 * @Description  
 * @Author  
 * @Date 2024-01-16 
 */

@Entity
@Data
@Table ( name ="u_act" )
@IdClass(UAct.UActId.class)
public class UAct implements Serializable {

	private static final long serialVersionUID =  1L;

    @Id
   	@Column(name = "aid" )
	private int aid;

    @Id
   	@Column(name = "uid" )
	private int uid;

	/**
	 * 这个活动开始时间(方便重复开启)
	 */
    @Id
   	@Column(name = "ts" )
	private int ts;

    @Id
   	@Column(name = "atype" )
	private String atype;

   	@Column(name = "avalue" )
	private String avalue;

	public class UActId implements Serializable {

	    @Id
	   	@Column(name = "aid" )
	    private int aid;

	    @Id
	   	@Column(name = "uid" )
	    private int uid;

	/**
	 * 这个活动开始时间(方便重复开启)
	 */
	    @Id
	   	@Column(name = "ts" )
	    private int ts;

	    @Id
	   	@Column(name = "atype" )
	    private String atype;
	}
}
