package com.androprogrammer.tutorials.listners;

import com.google.gson.JsonArray;

public interface ApiResponse {
	public void NetworkRequestCompleted(int apiCode, JsonArray response);
	public void networkError(int apiCode, String message);
	public void responseError(int apiCode, String message);
}
