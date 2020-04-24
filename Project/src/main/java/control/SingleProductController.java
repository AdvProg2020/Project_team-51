package control;

import model.People.Account;
import model.Product;

import java.util.ArrayList;

public class SingleProductController extends Controller {

    private Product product ;

    public SingleProductController(Account currentAccount) {
        super(currentAccount);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static String digest(){
        return null;
    }

    public static void addToCart(String seller){

    }

    public static ArrayList<String> showAttributes(){
        return null;
    }

    public static ArrayList<String> compare(String productId){
        return null;
    }

    public static void comment(String title , String content){

    }
}
