package com.netease.news.rec.light.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class TestHystrixCommand extends HystrixCommand<String> {
    private Logger testLogger = LoggerFactory.getLogger("TestLog");
	private int requestId;

	public TestHystrixCommand(int requestId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(HystrixGroupCore.TEST.getType()+"group"))
				.andCommandKey(HystrixCommandKey.Factory.asKey(HystrixGroupCore.TEST.getType()+"query"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(HystrixGroupCore.TEST.getType()+"threadpool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(10))//服务线程池数量
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionTimeoutInMilliseconds(100)
						.withCircuitBreakerErrorThresholdPercentage(5)//熔断器关闭到打开阈值
						.withCircuitBreakerSleepWindowInMilliseconds(130))//熔断器打开到关闭的时间窗长度
				);
		this.requestId=requestId;
	}

	@Override
	protected String run() throws Exception {
		long start=System.currentTimeMillis();
		try{
			if(requestId%29==0 || requestId%30==0 || requestId%31==0){
				Thread.sleep(201);
			}else if(requestId%26==0 || requestId%27==0 || requestId%28==0){
				Thread.sleep(50);
			}else if(requestId%23==0 || requestId%24==0 || requestId%25==0){
				Thread.sleep(20);
			}else{
				Thread.sleep(60);
			}
		}catch(Exception e){
			testLogger.warn("{} requestId:{},{}",HystrixGroupCore.TEST.getType(),this.requestId,e.getMessage());
		}
		long cost=System.currentTimeMillis()-start;
		testLogger.info("{} requestId:{},cost:{}ms,{}",
				HystrixGroupCore.TEST.getType(),this.requestId,cost,System.currentTimeMillis());
		return "ok"+this.requestId;
	}

	@Override
	protected String getFallback(){
		testLogger.warn("{} requestId:{},getFallback {}!",HystrixGroupCore.TEST.getType(),this.requestId,System.currentTimeMillis());
		return "fallback"+this.requestId;
	}
}
