/**
* Nombre del Programa : Log4jPrintWriter.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 23/07/2012
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.comun.util;

import java.io.PrintWriter;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class Log4jPrintWriter extends PrintWriter {
    Priority level;
    Category cat;
    StringBuffer text = new StringBuffer("");

    public Log4jPrintWriter(Category cat, Priority level) {
        super(System.err);  // PrintWriter doesn't have default constructor.
        this.level = level;
        this.cat   = cat;
        
        this.cat.log(this.level,"HERE WE GO!");
    }

    // overrides all the print and println methods for 'print' it to the constructor's Category
    public void close(){
        flush();
    }
    public void flush(){
        if (!text.toString().equals("")){
            cat.log(level,text.toString());
            text.setLength(0);
        }
    }
    public void print(boolean b){
        text.append(b);
    }

    public void print(char c){
        text.append(c);
    }
    public void print(char[] s){
        text.append(s);
    }
    public void print(double d){
        text.append(d);
    }
    public void print(float f){
        text.append(f);
    }
    public void print(int i){
        text.append(i);
    }
    public void print(long l){
        text.append(l);
    }
    public void print(Object obj){
        text.append(obj);
    }
    public void print(String s){
        text.append(s);
    }
    public void println(){
        if (!text.toString().equals("")){
            cat.log(level,text.toString());
            text.setLength(0);
        }
    }
    public void println(boolean x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(char x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(char[] x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(double x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(float x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(int x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(long x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(Object x){
        text.append(x);
        cat.log(level,text.toString());
        text.setLength(0);
    }
    public void println(String x){
        text.append(x);
        if (x.contains("hallados")){ 
        	if(x.contains("no errors")){        		
        		cat.log(level,text.toString());
        	}else{
        		cat.log(level,"Panda ERROR: " + text.toString());
        	}
        } else {
        	cat.log(level,text.toString());
        }
        text.setLength(0);
    }
}