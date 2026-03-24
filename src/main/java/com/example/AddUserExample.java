package com.example;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class AddUserExample {

    public static void main(String[] args) {
        String host = "192.168.88.1";
        String username = "admin";
        String password = "admin";
        String newUser = "testuser";

        try (ApiConnection con = ApiConnection.connect(host)) {
            con.login(username, password);
            System.out.println("Connected to Mikrotik!");

            // Step 1: Fetch current users
            Set<String> existingUsers = new HashSet<>();
            CountDownLatch fetchLatch = new CountDownLatch(1);

            con.execute("/user/print", new ResultListener() {
                @Override
                public void receive(Map<String, String> result) {
                    existingUsers.add(result.get("name"));
                }

                @Override
                public void completed() {
                    System.out.println("Finished fetching users.");
                    fetchLatch.countDown(); // signal that fetching is done
                }

                @Override
                public void error(MikrotikApiException ex) {
                    ex.printStackTrace();
                    fetchLatch.countDown(); // signal anyway
                }
            });

            // Wait until fetch is complete
            fetchLatch.await();

            // Step 2: Create user if not exists
            try {
                con.execute("/user/add name=" + newUser + " group=full password=1234");
                System.out.println("User '" + newUser + "' created successfully!");
            } catch (Exception e) {
                if (e.getMessage().contains("already exists")) {
                    System.out.println("User '" + newUser + "' already exists.");
                } else {
                    e.printStackTrace();
                }
            }

            // Step 3: Fetch and print all users
            Set<String> currentUsers = new HashSet<>();
            CountDownLatch printLatch = new CountDownLatch(1);

            con.execute("/user/print", new ResultListener() {
                @Override
                public void receive(Map<String, String> result) {
                    currentUsers.add(result.get("name"));
                }

                @Override
                public void completed() {
                    printLatch.countDown();
                }

                @Override
                public void error(MikrotikApiException ex) {
                    ex.printStackTrace();
                    printLatch.countDown();
                }
            });

            // Wait for fetch before printing
            printLatch.await();

            System.out.println("\nCurrent users:");
            for (String user : currentUsers) {
                System.out.println(user);
            }
            
         // Step 4: Fetch Mikrotik system info
            try {
                Map<String, String> info = MikrotikInfoFetcher.fetchSystemInfo(con);
                System.out.println("\nMikrotik System Info:");
                info.forEach((key, value) -> System.out.println(key + ": " + value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
}