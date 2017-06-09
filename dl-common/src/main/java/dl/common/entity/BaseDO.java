package dl.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/*
 * @MappedSuperclass 
 * 用在父类上面。当这个类肯定是父类时，加此标注。
 * 如果改成@Entity，则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
 * http://blog.sina.com.cn/s/blog_7085382f0100uk4p.html
 */
@MappedSuperclass
public abstract class BaseDO implements Serializable {

	private static final long serialVersionUID = 2516071385435721847L;

	protected Long id;
	
	protected Date created;
	
	protected Date lastUpdate;
	
	protected Boolean deleted=false;
	
	/**
	 * 创建人id
	 */
	protected Long creator;
	
	/**
	 * 最后修改人id
	 */
	protected Long lastModifier;
	
	/*
	 * 给实体的一个属性标识为数据库表中的主键时，可以使用@Id
	 */
	@Id
	/*
	 * 	GenerationType
	 *  JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO. 
		TABLE：使用一个特定的数据库表格来保存主键。 
		SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。 
		IDENTITY：主键由数据库自动生成（主要是自动增长型） 
		AUTO：主键由程序控制
	 */
	@GeneratedValue(strategy=GenerationType.AUTO, generator="hzm_dl")
	/*
	 * 	@SequenceGenerator
	 *  name属性表示该表主键生成策略的名称，它被引用在@GeneratedValue中设置的“generator”值中。 
		sequenceName属性表示生成策略用到的数据库序列名称。 
		initialValue表示主键初识值，默认为0。 
		allocationSize表示每次主键值增加的大小，例如设置成1，则表示每次创建新记录后自动加1，默认为50。
	 */
	@SequenceGenerator(name="hzm_dl",initialValue=10,sequenceName="hib_seq")
	/*
	 * @Column
	 * 此标记可以标注在getter方法或属性前
	 * unique属性表示该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。
       nullable属性表示该字段是否可以为null值，默认为true。
       insertable属性表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值。
       updatable属性表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的。
       columnDefinition属性表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用。若不指定该属性，通常使用默认的类型建表，若此时需要自定义建表的类型时，可在该属性中设置。
       table属性表示当映射多个表时，指定表的表中的字段。默认值为主表的表名。有关多个表的映射将在本章的5.6小节中详细讲述。
       length属性表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。
       precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。
	 * */
	@Column(length = 20)
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	@Column(name = "deleted")
	public Boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * 
	 * @Basic  实体Bean中所有的非Static 非transient的属性都可以被持久化,没有定义注解属性的等价于在其上添加了@Basic注解 
		通过@Basic注解可以声明属性的获取策略(lazy与否),默认的是即时获取(early fetch),这里又讨论到了 
		延迟关联获取和延迟属性获取,通常不需要对简单属性设置延迟获取,如需要定义@Basic(fetch=FetchType.LAZY) 
	 */
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public void setCreated() {
		this.created = new Date();
	}
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void setLastUpdate() {
		this.lastUpdate = new Date();
	}
	
	@Column(name = "creator")
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	@Column(name = "LAST_MODIFIER")
	public Long getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(Long lastModifier) {
		this.lastModifier = lastModifier;
	}
	
	
	
	
}