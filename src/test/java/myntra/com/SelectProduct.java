package myntra.com;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelectProduct {
	
	public WebDriver driver;
	
	@Test
	public void select_product() throws InterruptedException{
		
		driver=new ChromeDriver();
		driver.navigate().to("https://www.flipkart.com/");
		driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.name("q")).sendKeys("samsung s23 256gb", Keys.ENTER);//search product
		List<WebElement> ls=driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		for(WebElement temp:ls) {
			String device=temp.getText();
			if(device.equals("SAMSUNG Galaxy S23 5G (Phantom Black, 256 GB)")) {
				temp.click();
				break;
			}
		}
		ArrayList<String> ls1=new ArrayList<String>(driver.getWindowHandles());
		
		System.out.println(ls1);
		
		driver.switchTo().window(ls1.get(1));
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		try {
		
		driver.findElement(By.xpath("//button[normalize-space()='Add to cart']")).click();
		
		}catch(StaleElementReferenceException e) {
			driver.findElement(By.xpath("//button[normalize-space()='Add to cart']")).click();
			System.out.println(e.getMessage());
		}
		
		System.out.println("item added to cart successfully");
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Place Order']")).isDisplayed()); //true
		
		System.out.println("checkout page diaplyed");
			
		WebElement add=driver.findElement(By.xpath("//span[text()='Add Item']"));
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		
		js.executeScript("arguments[0].click();", add);
		
		Thread.sleep(2000);
		
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div[2]")).getText(), "Item added to cart" );
		
		System.out.println("protection added");
		
		WebElement remove=driver.findElement(By.xpath("//body/div[@id='container']/div/div/div/div/div/div/div/div/div/div/img[1]"));
		
		js.executeScript("arguments[0].click();", remove);
		
		WebElement rmvprotec=driver.findElement(By.xpath("//body[@class='fk-modal-visible']//div[@class='_2_e-g9 _2WFwmV']"));
		
		WebDriverWait wt=new WebDriverWait(driver, Duration.ofSeconds(5));
		wt.until(ExpectedConditions.visibilityOf(rmvprotec));
		
		WebElement ele3=driver.findElement(By.xpath("//body[@class='fk-modal-visible']//div[@class='_2_e-g9 _2WFwmV']//div[text()='Remove']"));
		
		js.executeScript("arguments[0].click();", ele3);
		
		Thread.sleep(2000);
		
		String actual = driver.findElement(By.xpath("//div[starts-with(text(),'Successfully')]")).getText();
		
		String expec="Successfully removed Samsung Care+ Screen Protection Plan 1 Year from your cart";
		
		//System.out.println(actual);
		
		Assert.assertEquals(actual,expec);
		
		System.out.println("protection removed");
		
		driver.quit();
	
	}

}
