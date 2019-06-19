package web;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "CheckImageServlet")
public class CheckImageServlet extends HttpServlet {
    private List<String> words=new ArrayList<String>();

    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("/WEB-INF/new_words.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        int width=180;
        int height=30;
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=bufferedImage.getGraphics();
        graphics.setColor(getRandColor(200,250));
        graphics.fillRect(0,0,width,height);
        graphics.setColor(Color.BLACK);

        graphics.drawRect(0,0,width-1,height-1);

        Graphics2D graphics2D=(Graphics2D)graphics;
        graphics2D.setFont(new Font("宋体",Font.BOLD,18));
        Random random = new Random();
        int index=random.nextInt(words.size());
        String word=words.get(index-1);
        //x坐标
        int x=10;
        for (int i=0;i<word.length();i++){
            graphics2D.setColor(getRandColor(20,110));
            int angle=random.nextInt(60)-30;
            double theta=angle*Math.PI/180;
            char c=word.charAt(i);
            graphics2D.rotate(theta,x,20);
            graphics2D.drawString(String.valueOf(c),x,20);
            graphics2D.rotate(-theta,x,20);
            // why???
            x+=40;

        }
        graphics.dispose();
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());

    }

    private Color getRandColor(int fc,int bc){
        Random random=new Random();
        if (fc>255)
            fc=255;
        if (fc<0)
            fc=0;
        if (bc>255)
            bc=255;
        if (bc<0)
            bc=0;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
