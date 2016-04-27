
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FileCompareServlet extends HttpServlet {

  static String s = "";
  static String l = "";
  static String s2 = "";
  static String s3 = "";

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, Exception {
      System.out.println("aa gaya");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String text1 = request.getParameter("baseText");
    String text2 = request.getParameter("newText");
//    String filePath1 = request.getParameter("file1");
//    String filePath2 = request.getParameter("file2");
    //System.out.println(filePath1);
    //HttpSession session = request.getSession();

//    File file = new File(filePath1);
    DocumentBuilder dBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc1 = dBuilder1.parse(new InputSource(new StringReader(text1)));
    //Document doc1 = dBuilder.parse(file);
    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    //System.out.println(doc.getChildNodes());
    if (doc1.hasChildNodes()) {
      s = "";
      printNote(doc1.getChildNodes());
      s2 = s;
    }
//    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    System.out.println("Name of the file to be matched");
//    String s1 = br.readLine();
//    File file1 = new File(filePath2);
    DocumentBuilder dBuilder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc2 = dBuilder2.parse(new InputSource(new StringReader(text2)));
//    Document doc2 = dBuilder2.parse(file1);
    if (doc2.hasChildNodes()) {
      s = "";
      printNote(doc2.getChildNodes());
      s3 = s;
    }
    float x = 0;
    x = s2.length();
    String str = lcs(s2, s3);
    float n = str.length();
    float y = 100 * n / x;
    float value = y;
    System.out.println("matched length is:" + n);
    System.out.println("main string lenght:" + x);
    //System.out.println("% matched:" + y);
    System.out.println(s2);
    x = 0;
    System.out.println("matched substring is:" + str);
    try {
//      out.println("<html>");
//      out.println("<head>");
//      out.println("<title>Servlet FileCompareServlet</title>");
//      out.println("</head>");
//      out.println("<body>");
//
//      out.println("<h1>Servlet FileCompareServlet at " + request.getContextPath() + "</h1>");
//      out.println("% matched:" + y);
//      out.println("</body>");
//      out.println("</html>");
//      out.println(temp + " and " + filePath1 + " and " + filePath2);
      //HttpSession session =request.getSession();

      request.setAttribute("text1",text1);
      request.setAttribute("text2",text2);
      request.setAttribute("value", value);
      System.out.println("jaa riya hu");
      getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
      //response.sendRedirect("index.jsp?param1="+y);
    } finally {
      out.close();
    }
  }
  static HashMap<Integer, String> h = new HashMap<Integer, String>();
  int i = 0;

  private static void printNote(NodeList childNodes) {
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node tempNode1 = childNodes.item(i);
      //System.out.println(childNodes.getLength());
      if (!tempNode1.getNodeName().equalsIgnoreCase("#text")) {
        //System.out.println("Node name is " + tempNode1.getNodeName());
      }
      if (tempNode1.hasChildNodes()) {
        int j;
        for (j = 0; j < tempNode1.getChildNodes().getLength(); j++) {
          Node node = tempNode1.getChildNodes().item(j);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            //System.out.println("node name: " + node.getNodeName());
            break;
          }
        }
        if (j < tempNode1.getChildNodes().getLength()) {
          printNote(tempNode1.getChildNodes());
          s = s.concat(tempNode1.getNodeName());
          //System.out.println("name is:"+s);
          //s="";

        } else {
          h.put(i, s);
          i++;
          s = s.concat(tempNode1.getNodeName());
          //System.out.println("name is:"+s);
        }
      }
    }
  }

  public static String lcs(String str1, String str2) {
    int l1 = str1.length();
    int l2 = str2.length();
    int[][] arr = new int[l1 + 1][l2 + 1];
    int len = 0, pos = -1;

    for (int x = 1; x < l1 + 1; x++) {
      for (int y = 1; y < l2 + 1; y++) {
        if (str1.charAt(x - 1) == str2.charAt(y - 1)) {
          arr[x][y] = arr[x - 1][y - 1] + 1;
          if (arr[x][y] > len) {
            len = arr[x][y];
            pos = x;
          }
        } else {
          arr[x][y] = 0;
        }
      }
    }
    return str1.substring(pos - len, pos);
  }
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      Logger.getLogger(FileCompareServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      Logger.getLogger(FileCompareServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
