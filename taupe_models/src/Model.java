

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Model {
    List<Triangle> triangle;   
    public Model(List<Triangle> t){
        triangle = t;
    }
    
    @SuppressWarnings("resource")
	public boolean loadFile(String url) throws Exception{
        BufferedReader bufferReader = null;
        FileReader fichierSrc = null;
        try{
            fichierSrc = new FileReader(url);
            bufferReader = new BufferedReader(fichierSrc);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return false;
        }
        
        /**
         * Analyser le fichier ....
         */
        
        /* extrait le nombre de points, segments et triangles indiqu�s par le fichier */
        String ligne = bufferReader.readLine();
        String str[] = ligne.split(" ");
        if(str.length!=3){
        	System.out.println("Erreur : Nombres de points, segments, triangle indiqu� incorrect");
        	return false;
        }
        for(int i=0; i<str.length; ++i){
        	if(!isNumber(str[i])){
        		System.out.println("Erreur : La premi�re ligne de "+url+" comporte des caract�res au lieu de nombres");
        		return false;
        	}		
        }
        int nbPts=Integer.parseInt(str[0]);
        int nbSeg=Integer.parseInt(str[1]);
        int nbTri=Integer.parseInt(str[2]);
        
        /* V�rifie la liste des points */
        bufferReader.readLine(); /* #point */
        if(!verifPoints(bufferReader, nbPts))
        	return false;
        
        /* V�rifie la liste des segments */
        if(!verifSegments(bufferReader, nbSeg, nbPts))
        	return false;
        
        /* V�rifie la liste des triangles */
        if(!verifTriangles(bufferReader, nbTri, nbSeg))
        	return false;
        
        bufferReader.close();
        return true;
    }
    
    public boolean isNumber(String s){
    	for(int i=0; i<s.length(); ++i){
    		if(!Character.isDigit(s.charAt(i)))
    				return false;
    	}
    	return true;
    }
    
    public boolean verifPoints(BufferedReader br, int nbPts) throws Exception{
    	String ligne;
    	List<String> points = new ArrayList<String>();
    	int i=0;
    	while(i<nbPts){
    		ligne=br.readLine();
    		if(ligne.charAt(0)=='#'){
    			System.out.println("Nombre de points incorrect");
    			return false;
    		}
    		String str[] = ligne.split(" ");
    		if(str.length!=3){
            	System.out.println("Erreur : Nombre de coordoonn�es d'un point incorrect");
            	return false;
            }
    		for(int j=0; j<str.length; ++j){
    			if(!isNumber(str[j])){
    				System.out.println("Erreur : Une coordonn�e d'un point n'est pas un nombre");
    				return false;
    			}
    		}
    		if(points.contains(ligne)){
    			System.out.println("Le point "+ligne+"est pr�sent plusieurs fois");
    			return false;
    		}
    		else{
    			points.add(ligne);
    			++i;
    		}
    	}
    	ligne=br.readLine();
    	return ligne.equals(new String("#segment"));
    }
    
    public boolean verifSegments(BufferedReader br, int nbSeg, int nbPts) throws Exception {
    	String ligne;
    	List<String> segments = new ArrayList<String>();
    	int i=0;
    	while(i<nbSeg){
    		ligne=br.readLine();
    		if(ligne.charAt(0)=='#'){
    			System.out.println("Nombre de segments incorrect");
    			return false;
    		}
    		String str[] = ligne.split(" ");
    		if(str.length!=2){
    			System.out.println("Erreur : Nombre de points d'un segment incorrect");
    			return false;
    		}
    		for(int j=0; j<str.length; ++j){
    			if(!isNumber(str[j])){
    				System.out.println("Erreur : Une coordonn�e d'un segment n'est pas un nombre");
    				return false;
    			}
    			if(Integer.parseInt(str[j])>nbPts){
    				System.out.println("Le point "+str[j]+" n'existe pas");
    				return false;
    			}
    		}
    		if(segments.contains(ligne)){
    			System.out.println("Le segment "+ligne+" est pr�sent plusieurs fois");
    			return false;
    		}
    		else{
    			segments.add(ligne);
    			++i;
    		}	
    	}
    	ligne=br.readLine();
    	return ligne.equals(new String("#triangle"));
    }
    
    public boolean verifTriangles(BufferedReader br, int nbTri, int nbSeg) throws Exception {
    	String ligne;
    	List<String> triangles = new ArrayList<String>();
    	int i=0;
    	while(i<nbTri){
    		ligne=br.readLine();
    		if(ligne==null){
    			System.out.println("Nombre de Triangles incorrect");
    			return false;
    		}
    		String str[] = ligne.split(" ");
    		if(str.length!=3){
    			System.out.println("Erreur : Nombre de segments d'un triangle incorrect");
    			return false;
    		}
    		for(int j=0; j<str.length; ++j){
    			if(!isNumber(str[j])){
    				System.out.println("Erreur : Une coordonn�e d'un triangle n'est pas un nombre");
    				return false;
    			}
    			if(Integer.parseInt(str[j])>nbSeg){
    				System.out.println("Le segment "+str[j]+" n'existe pas");
    				return false;
    			}
    		}
    		if(triangles.contains(ligne)){
    			System.out.println("Le triangle "+ligne+" est pr�sent plusieurs fois");
    			return false;
    		}
    		else{
    			triangles.add(ligne);
    			++i;
    		}
    	}
    	ligne=br.readLine();
    	return ligne==null;
    }
}
