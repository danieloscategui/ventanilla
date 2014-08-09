package com.pe.pgn.clubpgn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Version by Dan Kibler dan@getrolling.com
 *         Extended to implement Acegi GrantedAuthority interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "role")
@NamedQueries({
        @NamedQuery(
                name = "findRoleByName",
                query = "select r from Role r where r.name = :name "
        )
})
public class Role extends BaseObject implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 3690197650654049848L;
    private Long id;
    private String name;
    private String description;
    
    private List<OpcionMenu> opcionesMenu = new ArrayList<OpcionMenu>();

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Role() {
    }

    /**
     * Create a new instance and set the name.
     *
     * @param name name of the role.
     */
    public Role(final String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    /**
     * @return the name property (getAuthority required by Acegi's GrantedAuthority interface)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Transient
    public String getAuthority() {
        return getName();
    }

    @Column(length = 20)
    public String getName() {
        return this.name;
    }

    @Column(length = 64)
    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_opcion_menu",
            joinColumns = {@JoinColumn(name = "co_role")},
            inverseJoinColumns = @JoinColumn(name = "co_opcion_menu")
    )
    public List<OpcionMenu> getOpcionesMenu() {
		return opcionesMenu;
	}

	public void setOpcionesMenu(List<OpcionMenu> opcionesMenu) {
		this.opcionesMenu = opcionesMenu;
	}

	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }

        final Role role = (Role) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }
    
    

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        String role = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString().concat(" Opciones [ ");
        
        for(Iterator<OpcionMenu> iter = this.opcionesMenu.iterator(); iter.hasNext(); ){
        	
        	OpcionMenu opcionMenu = iter.next();
        	role = role.concat(opcionMenu.toString()).concat(", ");
        }
        
        role = role.concat("]");        
        return role;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Object o) {
        return (equals(o) ? 0 : -1);
    }
    
    public boolean tieneOpciones(){
    	for(Iterator<OpcionMenu> iter = this.opcionesMenu.iterator(); iter.hasNext(); ){
    		
    		OpcionMenu opcionMenu = iter.next();
    		if(opcionMenu.isStElegido()){
    			return true;
    		}
    	}
    	return false;
    }
}
