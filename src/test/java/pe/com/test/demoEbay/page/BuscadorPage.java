package pe.com.test.demoEbay.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pe.com.test.demoEbay.BuscadorTest;
import pe.com.test.demoEbay.driver.demoDriver;

public class BuscadorPage {
	
	private By btnCategoria = By.id("gh-shop-a");
	private By opcionCategoria = By.linkText("Calzado");
	private By opcionZapatos = By.xpath("//*[@id='mainContent']/section[2]/div[2]/a[2]/div[2]");
	private By flecha = By.id("w7-xCarousel-next");
	private String urlInicial;
	private WebDriver webDriver = null;
	private Actions builder;
	
	public BuscadorPage(String navegador, String urlInicial, boolean remoto){
		this.webDriver = demoDriver.inicializarDriver(navegador, remoto);
		this.urlInicial = urlInicial;
		this.builder =  new Actions(this.webDriver);
	}
	
	public List<BuscadorTest.Item> buscar() throws Exception{
		
		List<BuscadorTest.Item> lista;		
		this.webDriver.get(urlInicial);
		this.webDriver.findElement(btnCategoria).click();
		this.webDriver.findElement(opcionCategoria).click();		
		this.webDriver.findElement(opcionZapatos).click();	
		
		WebElement marca = 	this.webDriver.findElement(By.xpath("//*[@id='w7-xCarousel-x-carousel-items']/ul/li[5]/a/p"));
		while(!marca.isDisplayed()){
			this.webDriver.findElement(flecha).click();			
		} 
		marca.click();
		
		WebElement talla =  this.webDriver.findElement(By.xpath("//*[@id='w7-xCarousel-x-carousel-items']/ul/li[11]/a"));
		while(!talla.isDisplayed()){
			this.webDriver.findElement(flecha).click();			
		} 
		talla.click();		
		JavascriptExecutor je = (JavascriptExecutor) this.webDriver;
		je.executeScript("window.scrollBy(0,500);");		
		builder = new Actions(this.webDriver);
		WebElement ordenar = this.webDriver.findElement(By.xpath("//*[@id='w9-w0-w1']/button/div"));				
		builder.moveToElement(ordenar).perform();		
		WebElement opcion = this.webDriver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Duración: anuncios más recientes'])[1]/following::span[1]"));
		opcion.click();
		List<WebElement> listaObtenida = this.webDriver.findElements(By.className("s-item"));
		WebElement resultados = this.webDriver.findElement(By.className("srp-controls__count-heading"));
		String totalResultados = resultados.getText();
		totalResultados = totalResultados.split("de")[1];
		System.out.println("Total de resultados: " + totalResultados);
		System.out.println("Cantidad de registros en la vista: " + listaObtenida.size());
		lista = new ArrayList<BuscadorTest.Item>();
		for(int i=0;i<listaObtenida.size();i++){
			String nombre = listaObtenida.get(i).findElement(By.className("s-item__title")).getText();
			String strPrecio = listaObtenida.get(i).findElement(By.className("s-item__price")).getText();
			String strEnvio = listaObtenida.get(i).findElement(By.className("s-item__shipping")).getText();
			
			strPrecio = strPrecio.split("a")[0];
			strPrecio = strPrecio.replace("S/. ", "");
			strPrecio = strPrecio.replace(" ", "");
			strEnvio = strEnvio.replace("S/. ", "");
			strEnvio = strEnvio.replace(" por el envío", "");
			strEnvio = strEnvio.replace("Envío gratis", "0");
			
			Double precio = Double.parseDouble(strPrecio);
			Double envio = Double.parseDouble(strEnvio);
			//System.out.println("i:" + i + nombre+precio);
			lista.add(new BuscadorTest.Item(nombre,precio,envio));
		}
		
		
		return lista;
	}
	
	public void ordenar() throws Exception{
		WebElement ordenar = this.webDriver.findElement(By.xpath("//*[@id='w7-w0-w1']/button/div"));
		builder.moveToElement(ordenar).perform();		
		WebElement opcion = this.webDriver.findElement(By.xpath("//*[@id='w7-w0-w1']/div/div/ul/li[5]/a/span"));
		opcion.click();
	}
	
	public void cerrarPagina(){
		demoDriver.cerrarPagina(webDriver);
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}
}
