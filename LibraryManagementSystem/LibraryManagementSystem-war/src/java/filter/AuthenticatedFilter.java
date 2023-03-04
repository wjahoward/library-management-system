package filter;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managedbean.StaffManagedBean;

public class AuthenticatedFilter implements Filter {

    @Inject
    private StaffManagedBean staffManagedBean;

    public AuthenticatedFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request1 = (HttpServletRequest) request;        
        
        if (staffManagedBean == null
               /* || staffManagedBean.getStaffId() == null || staffManagedBean.getStaffId() == -1*/) {
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath() + "/login.xhtml");
        } else {
            //authenticated - continue 
            chain.doFilter(request1, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing 
    }

    @Override
    public void destroy() {
        //do nothing 
    }
}
