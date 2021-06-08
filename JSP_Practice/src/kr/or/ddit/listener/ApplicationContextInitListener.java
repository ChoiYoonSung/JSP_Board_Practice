package kr.or.ddit.listener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.or.ddit.context.ApplicationContext;

public class ApplicationContextInitListener implements ServletContextListener {

   public void contextInitialized(ServletContextEvent ctxEvent)  { 

      ServletContext ctx = ctxEvent.getServletContext();
      
      String beanConfigXml = ctx.getInitParameter("contextConfigLocation");
      
      if(beanConfigXml == null) return;
      
      beanConfigXml = ctx.getRealPath("/") + 
            beanConfigXml.replace("classpath:","WEB-INF/classes/").replace("/", File.separator);
      
      System.out.println(beanConfigXml);
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      
      try {
         DocumentBuilder documentBuilder = factory.newDocumentBuilder();
         Document document = documentBuilder.parse(beanConfigXml);
         Element root = document.getDocumentElement();
         //xml을 읽어서 객체화 시킨다. beans:root
         
         //<beans> <bean id="identify" class="class Type"></bean></beans>
         NodeList beans = root.getElementsByTagName("bean");
         Map<String, Object> applicationContext =
               ApplicationContext.getApplicationContext(); //application context
         System.out.println("인스턴스 생성");
         for(int i =0; i <beans.getLength(); i++) {
            //System.out.println(beans.item(i).getNodeName());
            
            Node bean = beans.item(i);
            if(bean.getNodeType() == Node.ELEMENT_NODE) {
               Element ele = (Element) bean;
               String id = ele.getAttribute("id");
               String classType = ele.getAttribute("class");
               
               System.out.printf("id: %s, class=%s\n", id, classType);
               /*D:\A_TeachingMaterial\6.JspSpring\workspace111\.metadata\.plugins\org.eclipse.wst.server.core
                * \tmp0\wtpwebapps\command_bootstrap\WEB-INF\classes\kr\or\ddit\context\application-context.xml
               id: sqlSessionFactory, class=com.jquery.mybatis.OracleMyBatisSqlSessionFactory
               id: menuDAO, class=com.jquery.dao.MenuDAOImpl
               id: menuService, class=com.jquery.service.MenuServiceImpl*/
               
               //map instance put
               Class<?> cls =Class.forName(classType);
               Object targetObj = cls.newInstance(); //single tone
               
               applicationContext.put(id, targetObj);
               
               System.out.println("id :" + id + ", class : " + classType);
            }
         }
         
         for(int i =0; i <beans.getLength(); i++) {
            Node bean = beans.item(i);
            if(bean.getNodeType() == Node.ELEMENT_NODE) {
               Element eleBean = (Element)bean;

               NodeList properties = bean.getChildNodes();
               for(int j=0; j < properties.getLength(); j++) {
                  Node property = properties.item(j);
                  if(property.getNodeType() == Node.ELEMENT_NODE) {
                     Element ele = (Element)property;
                     String name = ele.getAttribute("name");
                     String ref = ele.getAttribute("ref-value");
                     
                     //System.out.printf("name = %s, ref-value=%s\n", name, ref);
                     String setMethodName = "set" + name.substring(0,1).toUpperCase()
                                    + name.substring(1);
                     
                     String className = eleBean.getAttribute("class");
                     Class<?> classType = Class.forName(className);
                     
                     Method[] methods = classType.getMethods();
                     
                     for(Method method : methods) {
                        //의존성 여부 확인
                        if(method.getName().equals(setMethodName)){
                           method.invoke(applicationContext.get(eleBean.getAttribute("id")),
                                 applicationContext.get(ref));
                           
                           System.out.println("[invoke]"
                                 +applicationContext.get(eleBean.getAttribute("id"))
                                 +":"+applicationContext.get(ref));
                           
                        }
                     }
                     
                  }
                  
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
    public void contextDestroyed(ServletContextEvent ctxEvent)  { 

    }
    
   
}