package com.example;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MikrotikInfoFetcher {

    /**
     * Fetch basic system info from Mikrotik: name, CPU load, temperature
     * @param con ApiConnection already logged in
     * @return Map of info keys -> values
     * @throws InterruptedException
     */
    public static Map<String, String> fetchSystemInfo(ApiConnection con) throws InterruptedException {
        Map<String, String> systemInfo = new HashMap<>();
        CountDownLatch latch = new CountDownLatch(1);

        try {
			con.execute("/system/resource/print", new ResultListener() {
			    @Override
			    public void receive(Map<String, String> result) {
			        // Extract relevant info
			        systemInfo.put("name", result.get("name"));
			        systemInfo.put("cpu-load", result.get("cpu-load"));
			        systemInfo.put("temperature", result.get("temperature"));
			        systemInfo.put("uptime", result.get("uptime"));
			    }

			    @Override
			    public void completed() {
			        latch.countDown();
			    }

			    @Override
			    public void error(MikrotikApiException ex) {
			        ex.printStackTrace();
			        latch.countDown();
			    }
			});
		} catch (MikrotikApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Wait until the async fetch finishes
        latch.await();
        return systemInfo;
    }
}