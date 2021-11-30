package com.desafioCCS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadTransform {

	// Constante para sinalizar erro 500
	private static final String SERVER_ERROR = "SERVER_ERROR";

	/**
	 * Método para carregar e ordenar todos os números de todas as páginas.
	 * 
	 * @return Todos os Números
	 */
	public static BigDecimal[] loadAllNumbers() {

		int i = 0; // contador das páginas
		String content = "";
		BigDecimal[] allNumbers = {}; // BigDecimal devido ao número de casas decimais.
		ObjectMapper objectMapper = new ObjectMapper(); // Instância da Lib Jackson para converter JSON
		Page page = null;
		do {
			i++;
			System.out.println("processing page " + i + "... ");
			try {

				page = null;
				content = getUrlContent("http://challenge.dienekes.com.br/api/numbers?page=" + i);
				if (content.equals(SERVER_ERROR)) {
					System.out.println("page " + i + ": SERVER_ERROR (Will Retry)"); 
					i--;
				} else {
					page = objectMapper.readValue(content, Page.class); // Conversão de JSON para POJO Page
				}
			} catch (Exception e) {

				System.out.println("Error in page " + i);
				e.printStackTrace();
			}
			if (page != null) {
				allNumbers = ArrayUtils.addAll(allNumbers, page.getNumbers());
			}

		} while (page == null || page.getNumbers().length > 0);

		System.out.println(allNumbers.length);

		mergeSort(allNumbers, allNumbers.length);
		for (BigDecimal number : allNumbers) {
			System.out.println(number);

		}
		return allNumbers;
	}

	/**
	 * Método para ordenar Array.
	 * Ordenação feita com a técnica "Merge Sort".
	 * @param a Array
	 * @param n Tamanho do Array
	 * 
	 */
	private static void mergeSort(BigDecimal[] a, int n) {
		if (n < 2) {
			return;
		}
		int mid = n / 2;
		BigDecimal[] l = new BigDecimal[mid];
		BigDecimal[] r = new BigDecimal[n - mid];

		for (int i = 0; i < mid; i++) {
			l[i] = a[i];
		}
		for (int i = mid; i < n; i++) {
			r[i - mid] = a[i];
		}
		mergeSort(l, mid);
		mergeSort(r, n - mid);

		merge(a, l, r, mid, n - mid);
	}

	private static void merge(BigDecimal[] a, BigDecimal[] l, BigDecimal[] r, int left, int right) {

		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
			if (l[i].compareTo(r[j]) <= 0) {
				a[k++] = l[i++];
			} else {
				a[k++] = r[j++];
			}
		}
		while (i < left) {
			a[k++] = l[i++];
		}
		while (j < right) {
			a[k++] = r[j++];
		}
	}

	private static String getUrlContent(String urlToRead) throws Exception {

		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 500) {
			return SERVER_ERROR;
		} else {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				for (String line; (line = reader.readLine()) != null;) {
					result.append(line);
				}

			}

			return result.toString();
		}
	}

}
