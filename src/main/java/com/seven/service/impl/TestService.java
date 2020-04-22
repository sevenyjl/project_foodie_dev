package com.seven.service.impl;

import com.seven.dao.StockVersionDao;
import com.seven.entity.StockVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName TestService
 * @Description 测试
 * @Author seven
 * @Date 2020/4/16 10:38
 * @Version 1.0
 **/
@Service
public class TestService {
    @Autowired
    private StockVersionDao stockVersionDao;
    public void testDemo(String itemsSpecId){
        StockVersion stockVersion = new StockVersion();
        stockVersion.setItemsSpecId(itemsSpecId);
        //先判断itemsSpecId对应的版本是否存在
        StockVersion selectOne = stockVersionDao.selectOne(stockVersion);
        if (selectOne==null){
            //不存在，则添加
            stockVersionDao.insert(stockVersion);
            //再次查询
            selectOne= stockVersionDao.selectOne(stockVersion);
        }
        //获得版本号
        Integer version = selectOne.getVersion();
        //这里库存判断    todo
        //从数据库中获取最新版本，并与之比较
        if (stockVersionDao.selectOne(stockVersion).getVersion()==version) {
            version++;
            selectOne.setVersion(version);
            stockVersionDao.updateByPrimaryKey(selectOne);
            System.out.println(Thread.currentThread().getName()+"\t成功，当前版本："+stockVersionDao.selectByPrimaryKey(itemsSpecId).getVersion());

        }else {
            System.out.println(Thread.currentThread().getName()+"\t失败");
        }
    }

    /**
     * 这里 使用CAS来搞，version为库存
     * @param itemsSpecId
     */
    public void testDemo2(String itemsSpecId){
        //先查看当前库存
        StockVersion stockVersion = stockVersionDao.selectByPrimaryKey(itemsSpecId);
        Integer stock = stockVersion.getVersion();
        AtomicInteger atomicInteger = new AtomicInteger(stock);
        Integer except = stockVersionDao.selectByPrimaryKey(itemsSpecId).getVersion();
        stock--;
        while (atomicInteger.compareAndSet(except,stock)) {
            //判断库存是否充足
            if (stock<0){
                System.out.println(Thread.currentThread().getName()+"\t库存不足够");
                return;
            }
            StockVersion update = new StockVersion();
            update.setVersion(stock);
            update.setItemsSpecId(itemsSpecId);
            stockVersionDao.updateByPrimaryKey(update);
            System.out.println(Thread.currentThread().getName()+"\t成功，当前库存："+stockVersionDao.selectByPrimaryKey(itemsSpecId).getVersion());
        }

    }
}
