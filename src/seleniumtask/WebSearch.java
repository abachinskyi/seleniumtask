package seleniumtask;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebSearch {
	
	private static Scanner inputScanner;
	
	public static void main(String[] args) {
		
		System.out.println("Enter your search query, please:");
		inputScanner = new Scanner(System.in);
		String searchQuery = inputScanner.nextLine();
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		try{	
			driver.findElement(By.name("q"));
		} catch(Exception e){
			System.out.println("Error: check your Interrnet connection and try again.");
			driver.close();
			return;
		}
		WebElement searchField = driver.findElement(By.name("q"));
		searchField.sendKeys(searchQuery);
		searchField.submit();
		
		WebElement searchButton = driver.findElement(By.name("btnG"));
		try{
			searchButton.click();
		} catch (Exception e){
			System.out.println("No search query.");
			driver.close();
			return;
		}
		
		try{ 
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
		} catch (Exception e) {
			System.out.println("No results on this request. \nTry again.");
			driver.close();
			return;
		}
		
		List<WebElement> searchResultElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
		
		ArrayList<String> searchResultReferences = new ArrayList<String>() ;

		for (WebElement result : searchResultElements){
			searchResultReferences.add(result.getAttribute("href"));	
		}
		  	
    	for (int i = 0; i < searchResultReferences.size(); i++){
    		
    		try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		driver.get(searchResultReferences.get(i));
    		System.out.print(i+1);
    		System.out.println(". " + searchResultReferences.get(i));
    		try{
    			System.out.println("Title: " + driver.getTitle());
    		} catch(Exception ex){
    			System.out.println("No title.");
    		}
    		
    		driver.navigate().back();	
    		driver.switchTo().defaultContent();
    		
    		try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}    	
		driver.close();
		}
}
