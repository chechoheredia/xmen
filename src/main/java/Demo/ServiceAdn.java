
package Demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceAdn {
    @Autowired
    private RepositoryAdn repo;
    
    public List<adn> listAll(){
    return repo.findAll();
    }
    public void save(adn adn){
    repo.save(adn);
    }
    
}
