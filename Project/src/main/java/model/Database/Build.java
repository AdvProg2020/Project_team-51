package model.Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Build {

    Deserialize deserialize = new Deserialize();

    public final Function<Path,String> readContent = path -> {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };

    public final Consumer<String> manager = str -> {
        try {
            deserialize.deserializeManagers(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> customer = str -> {
        try {
            deserialize.deserializeCustomers(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> seller = str -> {
        try {
            deserialize.deserializeSellers(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> product = str -> {
        try {
            deserialize.deserializeProducts(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> offCode = str -> {
        try {
            deserialize.deserializeOffCodes(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> auction = str -> {
        try {
            deserialize.deserializeAuctions(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> category = str -> {
        try {
            deserialize.deserializeCategories(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> rate = str -> {
        try {
            deserialize.deserializeRates(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> comment = str -> {
        try {
            deserialize.deserializeComments(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> order = str -> {
        try {
            deserialize.deserializeOrders(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> itemOfOrder = str -> {
        try {
            deserialize.deserializeItemsOfOrders(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> addAuctionRequest = str -> {
        try {
            deserialize.deserializeAddAuctionRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> addSellerRequest = str -> {
        try {
            deserialize.deserializeAddSellerRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> addCommentRequest = str -> {
        try {
            deserialize.deserializeAddCommentRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> addItemRequest = str -> {
        try {
            deserialize.deserializeItemsOfOrders(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> editAuctionRequest = str -> {
        try {
            deserialize.deserializeEditAuctionRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> editProductRequest = str -> {
        try {
            deserialize.deserializeEditProductRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };



    public Build() throws IOException {

        Path path = Paths.get("Project" , "src" , "main" , "resources");
        Stream<Path> stream = Files.walk(path);
        Stream<File> dataBase = stream.filter(Files::isRegularFile).map(Path::toFile);

        dataBase.filter(file -> file.getName().startsWith("Manager")).map(File::toPath).map(readContent).forEach(manager);
        dataBase.filter(file -> file.getName().startsWith("Customer")).map(File::toPath).map(readContent).forEach(customer);
        dataBase.filter(file -> file.getName().startsWith("Seller")).map(File::toPath).map(readContent).forEach(seller);
        dataBase.filter(file -> file.getName().startsWith("PID")).map(File::toPath).map(readContent).forEach(product);
        dataBase.filter(file -> file.getName().startsWith("OFF")).map(File::toPath).map(readContent).forEach(offCode);
        dataBase.filter(file -> file.getName().startsWith("AUC")).map(File::toPath).map(readContent).forEach(auction);
        dataBase.filter(file -> file.getName().startsWith("CTG")).map(File::toPath).map(readContent).forEach(category);
        dataBase.filter(file -> file.getName().startsWith("RT")).map(File::toPath).map(readContent).forEach(rate);
        dataBase.filter(file -> file.getName().startsWith("CM")).map(File::toPath).map(readContent).forEach(comment);
        dataBase.filter(file -> file.getName().startsWith("Order")).map(File::toPath).map(readContent).forEach(order);
        dataBase.filter(file -> file.getName().startsWith("IOO")).map(File::toPath).map(readContent).forEach(itemOfOrder);
        dataBase.filter(file -> file.getName().startsWith("AA")).map(File::toPath).map(readContent).forEach(addAuctionRequest);
        dataBase.filter(file -> file.getName().startsWith("AS")).map(File::toPath).map(readContent).forEach(addSellerRequest);
        dataBase.filter(file -> file.getName().startsWith("AC")).map(File::toPath).map(readContent).forEach(addCommentRequest);
        dataBase.filter(file -> file.getName().startsWith("AI")).map(File::toPath).map(readContent).forEach(addItemRequest);
        dataBase.filter(file -> file.getName().startsWith("EA")).map(File::toPath).map(readContent).forEach(editAuctionRequest);
        dataBase.filter(file -> file.getName().startsWith("EP")).map(File::toPath).map(readContent).forEach(editProductRequest);

}
}
