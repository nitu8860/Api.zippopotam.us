package com.example.Api.zippopotam.us;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Application {

	public static void main(String[] args) throws Exception {

		URL getUrl = new URL("https://api.zippopotam.us/us/33162");
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();

		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder jsonResponseData = new StringBuilder();
			String readLine;

			while ((readLine = in.readLine()) != null) {
				jsonResponseData.append(readLine);
			}

			in.close();
			JSONObject data = new JSONObject(jsonResponseData.toString());

			String postCode = data.getString("post code");
			String country = data.getString("country");
			String countryAbbreviation = data.getString("country abbreviation");

			JSONArray places = data.getJSONArray("places");
			JSONObject place = places.getJSONObject(0);
			String placeName = place.getString("place name");
			String longitude = place.getString("longitude");
			String state = place.getString("state");
			String stateAbbreviation = place.getString("state abbreviation");
			String latitude = place.getString("latitude");

			JSONObject output = new JSONObject();
			output.put("post code", postCode);
			output.put("country", country);
			output.put("country abbreviation", countryAbbreviation);

			JSONArray placesArray = new JSONArray();
			JSONObject placeObject = new JSONObject();
			placeObject.put("place name", placeName);
			placeObject.put("longitude", longitude);
			placeObject.put("state", state);
			placeObject.put("state abbreviation", stateAbbreviation);
			placeObject.put("latitude", latitude);

			placesArray.put(placeObject);
			output.put("places", placesArray);

			System.out.println(output.toString(4));
		} else {
			System.out.println("This is not a valid URL - " + responseCode);
		}
	}
}
