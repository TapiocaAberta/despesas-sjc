package io.tapioca;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class BaixaDespesasSelenium {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String chromeDriver = "/home/pesilva/opt/browser_drivers/chromedriver_linux64/chromedriver";
        String despesasPage = "https://servicos.sjc.sp.gov.br/portal_da_transparencia/despesa_funcao.aspx";
        String despesasPath = "/home/pesilva/workspace/code/pessoal/data-etl/despesas_sjc/";
        
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", despesasPath);
        
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        
        System.setProperty("webdriver.chrome.driver",chromeDriver);
        ChromeDriver driver = new ChromeDriver(options);
        
        driver.get(despesasPage);
        
        int index = 1;
        int max = 155;
        
        while (index != max) {

            WebElement selectElement = driver.findElement(By.name("ctl00$cphConteudo$cmbPeriodo"));
            Select select = new Select(selectElement);
            
            select.selectByIndex(index);
            WebElement baixarCSV = driver.findElement(By.id("ctl00_cphConteudo_lnkExportarLista"));
            baixarCSV.click();

            WebElement voltar = driver.findElement(By.linkText("Voltar"));
            voltar.click();
            
            index++;
        }
        
        driver.quit();

    }

}
