import java.util.List;

import com.dao.ProduitDaoImpl;
import com.model.Produit;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProduitDaoImpl pdi=new ProduitDaoImpl();
		pdi.updateDISP();
		List<Produit>l=pdi.get3List();
		for (Produit p :l){
			System.out.println(p.getName()+" "+p.getStatus() );
		}
	}

}
