 package P1;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class DeletingSkillsSet 
{
	WebDriver driver; 
	String url = "https://opensource-demo.orangehrmlive.com/index.php/auth/login"; //url for OrangeHRM website

	By uname = By.id("txtUsername"); //xpath to enter username 
	By pwd = By.id("txtPassword"); //xpath to enter password
	By login = By.id("btnLogin"); //xpath to click login button
	
	By qualification = By.linkText("Qualifications"); //xpath to select "Qualifications" in the menu bar
	By skills = By.linkText("Skills"); //xpath to select "Skills" from the dropdown list
	
	By add = By.id("btnAdd"); //xpath for add button
	By skillName = By.id("skill_name"); //xpath to enter skill name
	By save = By.id("btnSave"); //xpath for save button
	
	By checkBox = By.xpath("//a[contains(text(),'Testing Demo')]/parent::td//preceding-sibling::td//input[@name='chkListRecord[]']");//xpath to select the skill from the data
	By delete = By.id("btnDel"); //xpath for delete button
	
	By skillList = By.xpath("//*[@id='recordsListTable']/tbody/tr/td[2]"); //xapth to get number of skills in the data
	
	By welcomeIcon = By.xpath("//a[@id='welcome']"); //xpath to select welcome or profile icon 
	By logout = By.xpath("//a[text()='Logout']"); //xpath to select logout from the dropdown list
	
	/********************** Invoke Browser **************************/
	
	public void createDriver()  //open Browser(Chrome/Firefox)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Browser name :"); //enter browser name in console
		String browserName = sc.next();
		
		if(browserName.equalsIgnoreCase("Chrome")) 
		{
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("Firefox")) 
		{
			driver = new FirefoxDriver();
		}
		else 
		{
			System.out.println("Please enter valid browser name.");
			System.exit(0);
		}		
	}
	
	/********************* Open URL **************************/
	
	public void openURL()
	{
		driver.manage().window().maximize(); //maximize the browser
		driver.get(url); //open the url
	}
	
	/********************** Login Page **************************/
	
    public void pageLogin() 
    { 
        driver.findElement(uname).sendKeys("Admin"); //enter the username as "Admin"
		driver.findElement(pwd).sendKeys("admin123"); //enter the password as "admin123"
		driver.findElement(login).click(); //click the login button
	}
    
	/********************** Add Skill **************************/
    
	public void addSkill() 
	{
		WebElement w1 = driver.findElement(By.linkText("Admin")); //move cursor to "Admin"
		Actions act = new Actions(driver); //Action class used to move mouse cursor
		act.moveToElement(w1).perform(); 
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(qualification).click(); //select "Qualifications" from menu bar
		driver.findElement(skills).click(); //select "Skills" from dropdown list
		driver.findElement(add).click(); //click "Add" button
		driver.findElement(skillName).sendKeys("Testing Demo"); //enter "Testing Demo" in the "Name" text box
		driver.findElement(save).click(); //click "Save" button
	}
	
	/********************** Delete Skill **************************/
	
	public void deleteSkill() 
	{
		driver.findElement(checkBox).click(); //check the "Testing Demo" checkbox
		driver.findElement(delete).click(); //click "Delete" button
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	/********************** Verify Deleted Skill **************************/
	
	public void verifyDeletedMessage()  //logic to verify whether the skill is deleted successfully
	{
		List<WebElement> lst1 = driver.findElements(skillList); 
		int count = 0;
		for(WebElement x : lst1) 
		{
			if(!x.getText().contains("Testing Demo")) 
			{
				count++;
			}
		}
		int size = lst1.size();
		if(count<=size) 
		{
			System.out.println("Successfully Deleted");
		}
		else 
		{
			System.out.println("Not deleted");
		}
	}
	
	/********************** Page Logout  **************************/
	
	public void pageLogout() throws Exception
	{ 
		driver.findElement(welcomeIcon).click(); //click profile icon in the right top corner
		Thread.sleep(2000);
        driver.findElement(logout).click(); //click "Logout"
        Thread.sleep(2000);
	}
	
	/********************* Close Browser **************************/
	
	public void closeBrowser() 
	{
		driver.close(); //close the browser
		driver.quit(); //quit the browser
	}
	
	/********************** Invoke the methods **************************/
	
	public static void main(String[] args) throws Exception
	{
		DeletingSkillsSet dss = new DeletingSkillsSet();
		dss.createDriver();
		dss.openURL();
		dss.pageLogin();
		dss.addSkill();
		dss.deleteSkill();
		dss.verifyDeletedMessage();
		dss.pageLogout();
		dss.closeBrowser();
	}
}
