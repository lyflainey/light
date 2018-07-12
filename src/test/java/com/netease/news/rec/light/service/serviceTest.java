package com.netease.news.rec.light.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netease.news.rec.light.hystrix.TestHystrixCommand;

import junit.framework.TestCase;

public class serviceTest extends TestCase{
    private Logger testLogger = LoggerFactory.getLogger("TestLog");
    
	public void testnewscmtHystrix(){
        long start=System.currentTimeMillis();
        for(int ind=1;ind<=1000;ind++){
    		TestHystrixCommand testHystrixCommand=new TestHystrixCommand(ind);
    		String result=testHystrixCommand.execute();
    		testLogger.warn("result:{}",result);
        }
        long cost=System.currentTimeMillis()-start;
        testLogger.warn("cost:{}ms",cost);
	}
}
