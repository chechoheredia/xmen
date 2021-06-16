
package Demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class adn {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;
    private String Codigo;
    private String Mutante;

    public adn() {
       super();
    }

    public adn(int Id, String Codigo, String Mutante) {
        this.Id = Id;
        this.Codigo = Codigo;
        this.Mutante = Mutante;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getMutante() {
        return Mutante;
    }

    public void setMutante(String Mutante) {
        this.Mutante = Mutante;
    }

  
    
}
