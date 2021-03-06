package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname=request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        rname=java.net.URLDecoder.decode(rname,"utf-8");//被逼使用该方法
        System.out.println(rname);
        int cid = 0;//类别id
        //2.处理参数
        if(cidStr != null && cidStr.length() > 0&& !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb,response);

    }
    /**
     * 根据rid查询单个旅游线路详细信息
     * ***/
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        int rid=0;
        if(ridStr != null && ridStr.length() > 0&& !"null".equals(ridStr)){
            rid = Integer.parseInt(ridStr);
        }
//        调用servlet查询
        Route route=routeService.findOne(rid);
        response.setContentType("application/json,utf-8");
        writeValue(route,response);
    }

}
