
package Demo;

import java.text.DecimalFormat;
import java.util.List;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ControllerAdn {
    @Autowired
    
    private ServiceAdn service;

    String msge;
    String tablaWeb;
    int coincidenciasWeb=0;

    @GetMapping("/")
    public String inicio(Model model){
    List<adn> listadn=service.listAll();
    model.addAttribute("listadn",listadn);
    return "Index";
}
    
    @GetMapping("/Stats")
    public String stats(Model model){
    List<adn> listadn=service.listAll();
    float humanos=0;
    float mutantes=0;
    String ratio;
    for (int i=0;i<listadn.size();i++){
       if(listadn.get(i).getMutante().equals("S")){
           mutantes++;
       }
           humanos++;
   }
   DecimalFormat formato=new DecimalFormat("#0.0");
   ratio=formato.format(mutantes/humanos);
   model.addAttribute("humanos",(int)humanos);
   model.addAttribute("mutantes",(int)mutantes);
   model.addAttribute("ratio",ratio);
   return "Stats";
    }
    
    @GetMapping("/Nuevo")
	    public String add(Model model) {
	        model.addAttribute("adn", new adn());
	        return "Nuevo";
	    }
    
    @RequestMapping(value="/Mutant",method=RequestMethod.POST)
    public String saveadn(@ModelAttribute("adn") adn adn){
            adn.setMutante("N");
            if(this.verAdn(adn)){
                 msge="Bienvenido a los XMEN! Tienes "+coincidenciasWeb+" coincidencias!";
                 adn.setMutante("S");
            }
            else{
            msge="Lo sentimos, eres un simple mortal! Tienes "+coincidenciasWeb+" coincidencia(s)!";
            adn.setMutante("N");
            }
        service.save(adn);
        return "redirect:/Resultado";
    }
    
    
    public boolean verAdn(adn adn){
    boolean resultado=false;
    int coincidenciaGeneral=0;
    String textoIngresado = adn.getCodigo();

        String[][] matrizX = new String[6][6];
        int contadorCadena = 0;
        for (int i = 0; i < 6; i++) {//Ciclo for para rellenar la matriz
            for (int j = 0; j < 6; j++) {
                matrizX[i][j] = textoIngresado.substring(contadorCadena, contadorCadena + 1);//Aqui esta mi problema
                contadorCadena++;
            }
        }

        tablaWeb="";
        tablaWeb="<table><td colspan='6'>Codigo gen&eacutetico</td>";
        for (int i = 0; i < 6; i++) {//Ciclo for para mostrar la matriz
            tablaWeb=tablaWeb+"<tr>";
            for (int j = 0; j < 6; j++) {
              tablaWeb=tablaWeb+"<td>"+matrizX[i][j] + "</td>";
                
            }
             tablaWeb=tablaWeb+"</tr>";
                  }
         tablaWeb=tablaWeb+"</table>";
   
        //VALIDACION HORIZONTAL
        int coincidenciasH=0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if(j<3){
                if (matrizX[i][j].equals(matrizX[i][j+1])) {
                      if (matrizX[i][j+2].equals(matrizX[i][j+3])) {
                          if (matrizX[i][j].equals(matrizX[i][j+3])) {
                              coincidenciasH++;
                          }
                      }
                }
                }
            }
            

        }
      
         //VALIDACION VERTICAL
        int coincidenciasV=0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if(j<3){
                if (matrizX[j][i].equals(matrizX[j+1][i])) {
                      if (matrizX[j+2][i].equals(matrizX[j+3][i])) {
                          if (matrizX[j][i].equals(matrizX[j+3][i])) {
                              coincidenciasV++;
                          }
                      }
                }
                }
            }
            

        }
       
         //VALIDACION DIAGONAL HACIA DERECHA
        int coincidenciasD=0;
        for (int j = 0; j < 3; j++) {
                if(j<3){
                //DIAGONAL PRINCIPAL    
                if (matrizX[j][j].equals(matrizX[j+1][j+1])) {
                      if (matrizX[j+2][j+2].equals(matrizX[j+3][j+3])) {
                          if (matrizX[j][j].equals(matrizX[j+3][j+3])) {
                              coincidenciasD++;
                          }
                      }
                }
                }
                if(j<2){
                //DIAGONALES ARRIBA
                 if (matrizX[j][j+1].equals(matrizX[j+1][j+2])) {
                      if (matrizX[j+2][j+3].equals(matrizX[j+3][j+4])) {
                          if (matrizX[j][j+1].equals(matrizX[j+3][j+4])) {
                              coincidenciasD++;
                          }
                      }
                }
                 
                //DIAGONALES ABAJO
                 if (matrizX[j+1][j].equals(matrizX[j+2][j+1])) {
                      if (matrizX[j+3][j+2].equals(matrizX[j+4][j+3])) {
                          if (matrizX[j+1][j].equals(matrizX[j+4][j+3])) {
                              coincidenciasD++;
                          }
                      }
                }
                }
                 if(j<1){
                //DIAGONALES ARRIBA
                 if (matrizX[j][j+2].equals(matrizX[j+1][j+3])) {
                      if (matrizX[j+2][j+4].equals(matrizX[j+3][j+5])) {
                          if (matrizX[j][j+2].equals(matrizX[j+3][j+5])) {
                              coincidenciasD++;
                          }
                      }
                }
                //DIAGONALES ABAJO
                 if (matrizX[j+2][j].equals(matrizX[j+3][j+1])) {
                      if (matrizX[j+4][j+2].equals(matrizX[j+5][j+3])) {
                          if (matrizX[j+2][j].equals(matrizX[j+5][j+3])) {
                              coincidenciasD++;
                          }
                      }
                }
                }
            }

        
        //VALIDACION DIAGONAL HACIA IZQUIERDA
        int coincidenciasI=0;
        for (int j = 0; j < 3; j++) {
                if(j<3){
                //DIAGONAL PRINCIPAL    
                if (matrizX[j][5-j].equals(matrizX[j+1][5-1-j])) {
                      if (matrizX[j+2][5-2-j].equals(matrizX[j+3][5-3-j])) {
                          if (matrizX[j][5-j].equals(matrizX[j+3][5-3-j])) {
                              coincidenciasI++;
                          }
                      }
                }
                }
                if(j<2){
                //DIAGONALES ARRIBA
              if (matrizX[j][5-j-1].equals(matrizX[j+1][5-1-j-1])) {
                      if (matrizX[j+2][5-2-j-1].equals(matrizX[j+3][5-3-j-1])) {
                          if (matrizX[j][5-j-1].equals(matrizX[j+3][5-3-j-1])) {
                              coincidenciasI++;
                          }
                      }
                }
                 
                //DIAGONALES ABAJO
            if (matrizX[j+1][5-j].equals(matrizX[j+2][5-j-1])) {
                      if (matrizX[j+3][5-2-j].equals(matrizX[j+4][5-3-j])) {
                          if (matrizX[j+1][5-j].equals(matrizX[j+4][5-3-j])) {
                              coincidenciasI++;
                          }
                      }
                }
                }
                 if(j<1){
       
                     //DIAGONALES ARRIBA
              if (matrizX[j][5-j-2].equals(matrizX[j+1][5-1-j-2])) {
                      if (matrizX[j+2][5-2-j-2].equals(matrizX[j+3][5-3-j-2])) {
                          if (matrizX[j][5-j-2].equals(matrizX[j+3][5-3-j-2])) {
                              coincidenciasI++;
                          }
                      }
                }
                 
                //DIAGONALES ABAJO
            if (matrizX[j+2][5-j].equals(matrizX[j+3][5-j-1])) {
                      if (matrizX[j+4][5-2-j].equals(matrizX[j+5][5-3-j])) {
                          if (matrizX[j+2][5-j].equals(matrizX[j+5][5-3-j])) {
                              coincidenciasI++;
                          }
                      }
                }
                }
        }

        
    coincidenciaGeneral=coincidenciasH+coincidenciasV+coincidenciasD+coincidenciasI;
    coincidenciasWeb=coincidenciaGeneral;
    if (coincidenciaGeneral>1){
   resultado=true;
    }
    return resultado;        
    }
    
 @RequestMapping("/Resultado")
    public @ResponseBody String resultado(){
    return "<h2>"+msge+"</h2>"+tablaWeb+"<h3><a href=\'\\'>Volver</a></h3>";
    }
}
