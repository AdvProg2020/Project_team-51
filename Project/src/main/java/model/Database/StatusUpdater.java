package model.Database;

import model.Auction;
import model.OffCode;
import model.Status;

import java.util.Date;

public class StatusUpdater implements Runnable {

    @Override
    public void run() {
        while (true) {
            updateAuctions();
            updateOffCodes();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAuctions() {
        var auctions = Auction.getAllAuctions();
        for (Auction auction : auctions) {
            var date = new Date();
            if (date.getTime() - auction.getEndDate().getTime() <= 0) {
                auction.setAuctionStatus(Status.ENDED);
            }
        }
    }

    private void updateOffCodes() {
        var offCodes = OffCode.getAllOffCodes();
        for (OffCode offCode : offCodes) {
            var date = new Date();
            if (date.getTime() - offCode.getEndDate().getTime() <= 0) {
                offCode.setStatus(Status.ENDED);
            }
        }
    }


}
