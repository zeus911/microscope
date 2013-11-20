package com.vipshop.microscope.trace;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;

import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.SecondaryCategory;

public class Tracer {
	
	/**
	 * Handle Method opeations.
	 * 
	 * @param spanName
	 * @param category
	 */
	public static void clientSend(String spanName, Category category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	/**
	 * Handle resteasy operations.
	 * 
	 * @param requestContext
	 * @param category
	 */
	public static void clientSend(ContainerRequestContext requestContext, Category category){
		TraceFactory.getHttpRequestHead(requestContext);
		TraceFactory.getTrace().clientSend(requestContext.getUriInfo().toString(), category);
	}
	
	/**
	 * Handle MyBATIS operations.
	 * 
	 * @param handler
	 * @param category
	 */
	public static void clientSend(RoutingStatementHandler handler, String serverIP, Category category) {
		TraceFactory.getTrace().clientSend(SecondaryCategory.buildName(handler), serverIP, category);
	}
	
	/**
	 * For httpclient 4.2 send request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpUriRequest request, Category category){
		TraceFactory.getTrace().clientSend(SecondaryCategory.buildName(request), category);
		TraceFactory.setHttpRequestHead(request);
	}
	
	/**
	 * For httpclient 4.2 send request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpRequest request, Category category){
		TraceFactory.getTrace().clientSend(SecondaryCategory.buildName((HttpUriRequest)request), category);
		TraceFactory.setHttpRequestHead(request);
	}
	
	/**
	 * For javax servlet receive http request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpServletRequest request, Object handler, Category category){
		TraceFactory.getHttpRequestHead(request);
		TraceFactory.getTrace().clientSend(SecondaryCategory.buildName(request, handler), category);
	}
	
	/**
	 * Set result code when exception happens.
	 * 
	 * @param e
	 */
	public static void setResultCode(String result) {
		Trace trace = TraceFactory.getContext();
		if (trace != null) {
			trace.setResutlCode(result);
		}
	}
	
	/**
	 * trace end api
	 */
	public static void clientReceive() {
		TraceFactory.getTrace().clientReceive();
	}
	
	/**
	 * app programmer api
	 * 
	 * @param key
	 * @param value
	 */
	public static void record(String key, String value) {
		Trace trace = TraceFactory.getContext();
		if (trace != null) {
			trace.record(key, value);
		}
	}
	
	/**
	 * asyn thread invoke.
	 * 
	 * @return
	 */
	public static Trace getContext() {
		return TraceFactory.getContext();
	}
	
	/**
	 * Set context
	 * 
	 * @param trace
	 */
	public static void setContext(Trace trace) {
		TraceFactory.setContext(trace);
	}
	
	/**
	 * integrate with other system.
	 * 
	 * @return
	 */
	public static long getTraceId() {
		return TraceFactory.getTraceId();
	}
	
}
