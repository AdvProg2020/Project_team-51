package model.Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Build implements Runnable {

    public final Function<Path, String> readContent = path -> {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };
    Deserialize deserialize = new Deserialize();

    public final Consumer<String> buyerLog = str -> {
        try {
            deserialize.deserializeBuyerLog(str);
        } catch (IOException e) {
            e.printStackTrace();
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
    public final Consumer<String> sellerLog = str -> {
        try {
            deserialize.deserializeSellerLog(str);
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
            deserialize.deserializeAddItemRequests(str);
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
    public final Consumer<String> addSellerForItemRequest = str -> {
        try {
            deserialize.deserializeAddSellerForItemRequests(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    public final Consumer<String> attribute = str -> {
        try {
            deserialize.deserializeAttributes(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    @Override
    public void run() {
        try {
            initialize();
        } catch (IOException e) {
            System.err.println("failed to build ! ");
        }
        while (true) {
            Thread thread2 = new Thread(new RuntimeDaraSaver());
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                System.out.println("shit !");
            }
        }
    }


    private Stream<File> createStream() throws IOException {
        Path path = Paths.get("Server", "src", "main", "resources");
        Stream<Path> stream = Files.walk(path);
        Stream<File> dataBase = stream.filter(Files::isRegularFile).map(Path::toFile);
        return dataBase;
    }

    public void initialize() throws IOException {
//        createStream().filter(file -> file.getName().startsWith("CTG")).map(File::toPath).map(readContent).forEach(category);
        createStream().filter(file -> file.getName().startsWith("Manager")).map(File::toPath).map(readContent).forEach(manager);
        createStream().filter(file -> file.getName().startsWith("Customer")).map(File::toPath).map(readContent).forEach(customer);
        createStream().filter(file -> file.getName().startsWith("Seller")).map(File::toPath).map(readContent).forEach(seller);
        createStream().filter(file -> file.getName().startsWith("PID")).map(File::toPath).map(readContent).forEach(product);
        createStream().filter(file -> file.getName().startsWith("OFF")).map(File::toPath).map(readContent).forEach(offCode);
        createStream().filter(file -> file.getName().startsWith("AUC")).map(File::toPath).map(readContent).forEach(auction);
        createStream().filter(file -> file.getName().startsWith("RT")).map(File::toPath).map(readContent).forEach(rate);
        createStream().filter(file -> file.getName().startsWith("CM")).map(File::toPath).map(readContent).forEach(comment);
        createStream().filter(file -> file.getName().startsWith("BL")).map(File::toPath).map(readContent).forEach(buyerLog);
        createStream().filter(file -> file.getName().startsWith("SL")).map(File::toPath).map(readContent).forEach(sellerLog);
        createStream().filter(file -> file.getName().startsWith("IOO")).map(File::toPath).map(readContent).forEach(itemOfOrder);
        createStream().filter(file -> file.getName().startsWith("AA")).map(File::toPath).map(readContent).forEach(addAuctionRequest);
        createStream().filter(file -> file.getName().startsWith("AS")).map(File::toPath).map(readContent).forEach(addSellerRequest);
        createStream().filter(file -> file.getName().startsWith("AC")).map(File::toPath).map(readContent).forEach(addCommentRequest);
        createStream().filter(file -> file.getName().startsWith("AI")).map(File::toPath).map(readContent).forEach(addItemRequest);
        createStream().filter(file -> file.getName().startsWith("EA")).map(File::toPath).map(readContent).forEach(editAuctionRequest);
        createStream().filter(file -> file.getName().startsWith("EP")).map(File::toPath).map(readContent).forEach(editProductRequest);
        createStream().filter(file -> file.getName().startsWith("ATB")).map(File::toPath).map(readContent).forEach(attribute);
        createStream().filter(file -> file.getName().startsWith("ASI")).map(File::toPath).map(readContent).forEach(addSellerForItemRequest);
    }

}
