import java.util.*;

public class capitalGains {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of transactions performed:");
        int numberOfTransactions = sc.nextInt();

        Queue<Transaction> queue = new LinkedList<>();
        for (int i = 1; i <= numberOfTransactions; i++) {
            System.out.println("Enter the number of shares purchased and the share price for transaction " + i + ":");
            int numberOfShares = sc.nextInt();
            int sharePrice = sc.nextInt();
            queue.offer(new Transaction(numberOfShares, sharePrice));
        }

        System.out.println("Enter the number of shares sold and the selling price:");
        int numberOfSharesSold = sc.nextInt();
        int sellingPrice = sc.nextInt();

        int totalSharesPurchased = getTotalSharesPurchased(queue);
        if (numberOfSharesSold > totalSharesPurchased) {
            System.out.println("the number of shares sold is greater than the number of shares purchased");
        } else {
            int capitalGain = calculateCapitalGain(queue, numberOfSharesSold, sellingPrice);
            if (capitalGain >= 0) {
                System.out.println("Total Capital gain: $" + capitalGain);
            } else {
                System.out.println("Total Capital gain: $" + (-1) * capitalGain);
            }
        }

        sc.close();
    }

    private static int getTotalSharesPurchased(Queue<Transaction> queue) {
        int totalShares = 0;
        for (Transaction transaction : queue) {
            totalShares += transaction.getNumberOfShares();
        }
        return totalShares;
    }

    private static int calculateCapitalGain(Queue<Transaction> queue, int numberOfSharesSold, int sellingPrice) {
        int capitalGain = 0;
        int sharesSold = 0;

        while (!queue.isEmpty() && sharesSold < numberOfSharesSold) {
            Transaction transaction = queue.peek();
            int numberOfShares = transaction.getNumberOfShares();
            int sharePrice = transaction.getSharePrice();
            int maxSharesSold = Math.min(numberOfShares, numberOfSharesSold - sharesSold);
            int saleValue = sellingPrice * maxSharesSold;
            int totalCost = 0;
            for (int i = 0; i < maxSharesSold; i++) {
                totalCost += sharePrice;

                if (sharePrice > sellingPrice) {
                    capitalGain += (sharePrice - sellingPrice) * 1;
                } else {
                    capitalGain += (sellingPrice - sharePrice) * -1;
                }
                sharesSold++;
                if (sharesSold == numberOfSharesSold) {
                    break;
                }
            }
            if (maxSharesSold == numberOfShares) {
                queue.poll();
            } else {
                queue.peek().setNumberOfShares(numberOfShares - maxSharesSold);
            }
        }
        return capitalGain;
    }
    private static class Transaction {
        private int numberOfShares;
        private int sharePrice;

        public Transaction(int shares, int price) {
            this.numberOfShares = shares;
            this.sharePrice = price;
        }

        public int getNumberOfShares() {
            return numberOfShares;
        }

        public int getSharePrice() {
            return sharePrice;
        }

        public void setNumberOfShares(int numberOfShares) {
            this.numberOfShares = numberOfShares;
        }
    }
}


