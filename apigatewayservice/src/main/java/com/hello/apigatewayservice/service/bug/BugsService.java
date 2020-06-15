package com.hello.apigatewayservice.service.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hello.apigatewayservice.config.AppConfig;
import com.hello.common.dto.olis.*;
import com.hello.apigatewayservice.repository.ThreeLevelWithYearRepository;
import com.hello.apigatewayservice.repository.brand.BrandRepository;
import com.hello.apigatewayservice.repository.engine.EngineRepository;
import com.hello.apigatewayservice.repository.engine.EngineTypeRepository;
import com.hello.apigatewayservice.repository.imgBase.ImgBaseRepository;
import com.hello.apigatewayservice.repository.imgBase.ImgMaterialRepository;
import com.hello.apigatewayservice.repository.olis.OneLevelCarTypeRepository;
import com.hello.apigatewayservice.repository.olis.ThreeLevelCarTypeRepository;
import com.hello.apigatewayservice.repository.olis.TwoLevelCarTypeRepository;
import com.hello.apigatewayservice.repository.saeDesc.SaeDescRepository;
import com.hello.apigatewayservice.repository.year.YearRepository;
import com.hello.apigatewayservice.service.FileService;
import com.hello.apigatewayservice.util.StringUtil;
import com.hello.common.entity.system.UploadFile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/22  17:25
 */
@Service
@Transactional
public class BugsService {

    final static String requestHomeUrl = "http://zqyy.auren.cn:8888/Handler1.ashx";
    private List<Brand> brandList = new ArrayList<>();
    private Map<String, ImgMaterial> materialMap = new ConcurrentHashMap<>();
    private Map<String, EngineType> engineTypeHashMap = new ConcurrentHashMap<>();
    private Map<String, Year> yearHashMap = new ConcurrentHashMap<>();
    private Map<String, ImgMaterial> olisMap = new ConcurrentHashMap<>();


    @Resource
    AppConfig appConfig;

    @Autowired
    FileService fileService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    OneLevelCarTypeRepository one;

    @Autowired
    TwoLevelCarTypeRepository two;

    @Autowired
    ThreeLevelCarTypeRepository three;

    @Autowired
    YearRepository yearRepository;

    @Autowired
    EngineTypeRepository engineTypeRepository;

    @Autowired
    EngineRepository engineRepository;

    @Autowired
    SaeDescRepository saeDescRepository;

    @Autowired
    ImgBaseRepository imgBaseRepository;

    @Autowired
    ImgMaterialRepository imgMaterialRepository;

    @Autowired
    ThreeLevelWithYearRepository threeLevelWithYearRepository;


    public void bugs() {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Cast the object to its class type
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

        //线程执行前
        System.out.println("核心线程: " + pool.getCorePoolSize());
        System.out.println("最大线程数量: " + pool.getLargestPoolSize());
        System.out.println("允许的最大线程: " + pool.getMaximumPoolSize());
        System.out.println("当前线程池线程数量: " + pool.getPoolSize());
        System.out.println("当前正在执行线程数量: " + pool.getActiveCount());
        System.out.println("总线程数量: " + pool.getTaskCount());

        Long startTime = System.currentTimeMillis();
        System.out.print("开始时间=========>" + startTime);
        RequestParams requestParams = new RequestParams(requestHomeUrl + "?type=getpinpai", "*/*", "Keep-Alive", "", requestHomeUrl + "?type=getpinpai");
        String requestResult = requestNet(requestParams);
        if (requestResult != "") {
            requestResult = requestResult.replaceAll("\\\\", "");
            requestResult = requestResult.substring(1, requestResult.length() - 1);
            //System.out.println(requestResult);
            JSONObject json = JSON.parseObject(requestResult);
            JSONArray resultList = json.getJSONArray("pinpailist");
            //品牌结果列表
            for (int k = resultList.size() - 1; k >= 0; k--) {
                analysisBrandList(resultList, k, requestParams);

                //executor.submit(new Task(resultList,k,requestParams));
            }

        } else {
            System.out.print("返回结果为空！");
        }
        materialMap.clear();
        engineTypeHashMap.clear();
        yearHashMap.clear();
        olisMap.clear();
        Long endTime = System.currentTimeMillis();
        System.out.print("结束时间==========>" + System.currentTimeMillis());
        System.out.print("花费时间==========>" + ((endTime - startTime) / 1000) + "秒");

        //线程执行后
        System.out.println("核心线程: " + pool.getCorePoolSize());
        System.out.println("最大线程数量: " + pool.getLargestPoolSize());
        System.out.println("允许的最大线程: " + pool.getMaximumPoolSize());
        System.out.println("当前线程池线程数量: " + pool.getPoolSize());
        System.out.println("当前正在执行线程数量: " + pool.getActiveCount());
        System.out.println("总线程数量: " + pool.getTaskCount());

        executor.shutdown();
    }

    class Task implements Runnable {
        private int i;
        private JSONArray resultList;
        private RequestParams requestParams;

        public Task(JSONArray resultList, int i, RequestParams requestParams) {
            this.resultList = resultList;
            this.i = i;
            this.requestParams = requestParams;
        }

        @Override
        public void run() {
            System.out.println(this.resultList + "," + this.i + "," + this.requestParams);

            analysisBrandList(this.resultList, this.i, this.requestParams);
            System.out.println("Running Task! Thread Name: " + Thread.currentThread().getName());
            System.out.println("Task Completed! Thread Name: " + Thread.currentThread().getName());

        }
    }

    private void analysisBrandList(JSONArray resultList, int i, RequestParams requestParams) {
        Map<String, Object> map = (Map<String, Object>) resultList.get(i);
        String brandName = map.get("brandName").toString();//名字
        String id = map.get("id").toString();//id
        String imgName = map.get("imgUrl").toString();//品牌图片名字
        String imgUrl = "http://47.105.96.134:8081/" + map.get("imgUrl").toString();//品牌图片路径
        String startAirm = map.get("startAirm").toString();//排序，大写拼音字母

        //保存品牌信息
        Brand brand = new Brand();
        brand.setSystemType("aorun");
        brand.setBrandFistName(startAirm);
        brand.setBrandName(brandName);
        UploadFile uploadFile = fileService.uploadFromWeb(imgUrl, appConfig.getBrand());
        brand.setUploadFile(uploadFile);
        brand = brandRepository.save(brand);
        //解析子车型列表
        requestParams.setRequestUrl(requestHomeUrl + "?type=gettypename&keyword=" + id);
        analysisCarTypeList(requestParams, brand, id);

    }


    private void analysisCarTypeList(RequestParams requestParams, Brand brand, String webBrandId) {
        //品牌子车型路径
        String result = requestNet(requestParams);
        result = result.replaceAll("\\\\", "");
        result = result.substring(1, result.length() - 1);
        //System.out.println(result);
        JSONObject json = JSON.parseObject(result);
        JSONArray carshuzhiResultList = json.getJSONArray("carshuzhi");
        //循环一级车型
        // List<OneLevelCarType> oneLevelCarTypeList=new ArrayList<>();
        // brand.setOneLevelCarTypeList(oneLevelCarTypeList);
        for (int i = 0; i < carshuzhiResultList.size(); i++) {
            Map map = (Map<String, Object>) carshuzhiResultList.get(i);
            String id = map.get("id").toString();
            String typeName = map.get("typename").toString();//一级车型名字

            //保存一级车型信息
            OneLevelCarType oneLevelCarType = new OneLevelCarType();
            oneLevelCarType.setSystemType(brand.getSystemType());
            oneLevelCarType.setCarTypeName(typeName);
            oneLevelCarType.setBrand(brand);
            oneLevelCarType = one.save(oneLevelCarType);
            // oneLevelCarTypeList.add(oneLevelCarType);
            String requestUrl = requestHomeUrl + "?type=getcar&keyword=" + id;
            requestParams.setRequestUrl(requestUrl);
            result = requestNet(requestParams);
            result = result.replaceAll("\\\\", "");
            result = result.substring(1, result.length() - 1);
            //System.out.println(result);
            json = JSON.parseObject(result);
            JSONArray sonCarTypeList = json.getJSONArray("carname");

            Map<String, Object> carTypeMap = new HashMap<>();
            List<Map<String, Object>> allSonCarTypeList = new ArrayList<>();


            for (int s = 0; s < sonCarTypeList.size(); s++) {
                Map<String, Object> sonCarTypeMap = (Map<String, Object>) sonCarTypeList.get(s);
                String sonCarTypeId = sonCarTypeMap.get("id").toString();
                String sonCatTypeParentId = sonCarTypeMap.get("parentid").toString();
                String sonCatTypetypename = sonCarTypeMap.get("typename").toString();//二级车型名字
                List<Map<String, Object>> carTypeList = new ArrayList<>();


                for (int j = 0; j < sonCarTypeList.size(); j++) {
                    Map<String, Object> sonCarTypeMap2 = (Map<String, Object>) sonCarTypeList.get(j);
                    String sonCarTypeId2 = sonCarTypeMap2.get("id").toString();
                    String sonCatTypeParentId2 = sonCarTypeMap2.get("parentid").toString();
                    String sonCatTypetypename2 = sonCarTypeMap2.get("typename").toString();
                    if (sonCarTypeId.equals(sonCatTypeParentId2)) {
                        if (carTypeMap.get(sonCatTypetypename) == null) {
                            carTypeMap.put(sonCatTypetypename, carTypeList);
                        }
                        carTypeList = (List<Map<String, Object>>) carTypeMap.get(sonCatTypetypename);
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("typename", sonCatTypetypename2);
                        map1.put("id", sonCarTypeId2);
                        map1.put("parentid", sonCatTypeParentId2);
                        carTypeList.add(map1);
                    }
                }
                if (carTypeList.size() != 0) {
                    carTypeMap.put(sonCatTypetypename, carTypeList);
                }
            }
            for (String key : carTypeMap.keySet()) {
                Map<String, Object> map1 = new HashMap();
                map1.put("sonCarTypeInfo", carTypeMap.get(key));
                map1.put("sonCarName", key);
                allSonCarTypeList.add(map1);
            }
            map.put("sonCarTypeMap", allSonCarTypeList);
            //List<TwoLevelCarType> twoLevelCarTypeList=new ArrayList<>();
            //oneLevelCarType.setTwoLevelCarTypeList(twoLevelCarTypeList);
            //针对每个汽车类目循环获取子车信息，然后获取每种型号汽车对应年限和发动机，用油推荐信息
            //循环二级车型
            for (int d = 0; d < allSonCarTypeList.size(); d++) {
                List<Map<String, Object>> carTypeList = (List<Map<String, Object>>) allSonCarTypeList.get(d).get("sonCarTypeInfo");
                String bigCarTypeName = allSonCarTypeList.get(d).get("sonCarName").toString();//二级车型名字

                //保存二级车型信息
                TwoLevelCarType twoLevelCarType = new TwoLevelCarType();
                twoLevelCarType.setSystemType(brand.getSystemType());
                twoLevelCarType.setCarTypeName(bigCarTypeName);
                twoLevelCarType.setOne(oneLevelCarType);
                twoLevelCarType = two.save(twoLevelCarType);
                // twoLevelCarTypeList.add(twoLevelCarType);

                //List<ThreeLevelCarType> threeLevelCarTypeList=new ArrayList<>();
                //twoLevelCarType.setThreeLevelCarTypeList(threeLevelCarTypeList);
                //获取每个详细汽车年限信息
                for (Map<String, Object> m : carTypeList) {
                    String id2 = m.get("id").toString();
                    String sonCarTypeName = m.get("typename").toString();//三级车型名字

                    //保存三级车型信息
                    ThreeLevelCarType threeLevelCarType = new ThreeLevelCarType();
                    threeLevelCarType.setSystemType(brand.getSystemType());
                    threeLevelCarType.setCarTypeName(sonCarTypeName);
                    threeLevelCarType.setTwo(twoLevelCarType);
                    threeLevelCarType = three.save(threeLevelCarType);
                    // threeLevelCarTypeList.add(threeLevelCarType);

                    requestUrl = requestHomeUrl + "?type=getyear&keyword=" + id2;
                    requestParams.setRequestUrl(requestUrl);
                    result = requestNet(requestParams);
                    result = result.replaceAll("\\\\", "");
                    result = result.substring(1, result.length() - 1);
                    // System.out.println(result);
                    json = JSON.parseObject(result);
                    JSONArray carYear = json.getJSONArray("yearlist");
                    // List<Year> yearList=new ArrayList<>();
                    // threeLevelCarType.setYearList(yearList);

                    //循环年限列表
                    for (int j = 0; j < carYear.size(); j++) {
                        Map<String, Object> carYearMap = (Map<String, Object>) carYear.get(j);
                        String yearId = carYearMap.get("id").toString();
                        String yearKewordsName = carYearMap.get("descriptions").toString();//年限关键字
                        String startYear = carYearMap.get("yearForm").toString();//年限开始
                        String endYear = carYearMap.get("yearTo").toString();//年限结束

                        Year year = null;

                        //判断是否存在重复
                        String unniYear = brand.getSystemType() + yearKewordsName;
                        if (yearHashMap.containsKey(unniYear)) {
                            year = yearHashMap.get(unniYear);
                        } else {
                            //保存年限信息
                            year = new Year();
                            year.setSystemType(brand.getSystemType());
                            year.setEndYear(endYear);
                            year.setStartYear(startYear);
                            year.setKeyYearWords(yearKewordsName);
                            year = yearRepository.save(year);
                            yearHashMap.put(unniYear, year);
                        }
                        //获取发动机类型信息
                        requestUrl = requestHomeUrl + "?type=getfdj&cartypeid=" + id2 + "&yearid=" + yearId;
                        requestParams.setRequestUrl(requestUrl);
                        result = requestNet(requestParams);
                        result = result.replaceAll("\\\\", "");
                        result = result.substring(1, result.length() - 1);
                        // System.out.println(result);
                        json = JSON.parseObject(result);
                        JSONArray fdjlArray = json.getJSONArray("fdjlist");
                        //List<EngineType> engineTypeList = new ArrayList<>();
                        //year.setEngineTypeList(engineTypeList);

                        //循环发动机类型
                        for (int a = 0; a < fdjlArray.size(); a++) {
                            Map<String, Object> fdjMap = (Map<String, Object>) fdjlArray.get(a);

                            String fdjId = fdjMap.get("id").toString();
                            String engineTypeName = fdjMap.get("typeName").toString();//发动机类型名字

                            EngineType engineType = null;
                            //判断是否存在发动机类型重复
                            String uuniEngineType = year.getId() + brand.getSystemType() + engineTypeName;
                            if (engineTypeHashMap.containsKey(uuniEngineType)) {
                                engineType = engineTypeHashMap.get(uuniEngineType);
                            } else {
                                engineType = new EngineType();
                                engineType.setSystemType(brand.getSystemType());
                                engineType.setEngineTypeName(engineTypeName);
                                engineType.setYear(year);
                                engineType = engineTypeRepository.save(engineType);
                                engineTypeHashMap.put(uuniEngineType, engineType);

                            }
                            //发动机信息
                            requestUrl = requestHomeUrl + "?type=getpic&fdjid=" + fdjId + "&cartypeid=" + id2;
                            requestParams.setRequestUrl(requestUrl);
                            result = requestNet(requestParams);
                            result = result.replaceAll("\\\\", "");
                            // System.out.println("发动机==========》:" + result);

                            result = result.substring(1, result.length() - 1);
                            //  System.out.println(result);
                            json = JSON.parseObject(result);
                            JSONArray piclist = json.getJSONArray("piclist");
                            String engineImageUrl = "http://47.105.96.134:8081/" + ((Map<String, Object>) piclist.get(0)).get("imgUrl").toString();//发动机图片下载地址
                            String id3 = ((Map<String, Object>) piclist.get(0)).get("id").toString();
                            String engineName = ((Map<String, Object>) piclist.get(0)).get("powerName").toString();//发动机名字
                            String engineKm = ((Map<String, Object>) piclist.get(0)).get("gonglishu").toString();//发动机公里
                            String engineOrder = ((Map<String, Object>) piclist.get(0)).get("orders").toString();//发动机排序

                            ImgMaterial imgMaterial = null;
                            UploadFile uploadFile = null;
                            ImgBase imgBase = null;
                            //判断发动机图片是否存在重复
                            if (materialMap.containsKey(engineName)) {
                                imgMaterial = materialMap.get(engineName);

                            } else {
                                //保存发动机图片信息
                                uploadFile = fileService.uploadFromWeb(engineImageUrl, appConfig.getEngine());
                                imgBase = new ImgBase();
                                imgMaterial = new ImgMaterial();
                                imgMaterial.setUploadFile(uploadFile);
                                imgMaterial.setMaterialName(engineName);
                                imgMaterial.setMaterialType(1);
                                imgMaterial.setMaterialKm(engineKm);
                                if (StringUtil.isNotEmpty(engineOrder))
                                    imgMaterial.setMaterialRank(Integer.parseInt(engineOrder));
                                imgMaterial = imgMaterialRepository.save(imgMaterial);
                                imgBase.setSystemType(brand.getSystemType());
                                imgBase.setImgMaterial(imgMaterial);
                                imgBaseRepository.save(imgBase);
                                materialMap.put(engineName, imgMaterial);
                            }

                            //保存发动机信息
                            //保存发动机信息
                            Engine engine = new Engine();
                            engine.setBrand(brand);
                            engine.setOne(oneLevelCarType);
                            engine.setTwo(twoLevelCarType);
                            engine.setThree(threeLevelCarType);
                            engine.setYear(year);
                            engine.setSystemType(brand.getSystemType());
                            engine.setEngineType(engineType);
                            engine.setImgMaterial(imgMaterial);
                            engine = engineRepository.save(engine);


                            //保存年限和三级车型对应表
                            ThreeLevelWithYear threeLevelWithYear = new ThreeLevelWithYear();
                            threeLevelWithYear.setYear(year);
                            threeLevelWithYear.setSystemType(brand.getSystemType());
                            threeLevelWithYear.setThree(threeLevelCarType);
                            threeLevelWithYear.setEngine(engine);
                            threeLevelWithYear.setEngineType(engineType);
                            threeLevelWithYearRepository.save(threeLevelWithYear);

                            //油品信息
                            requestUrl = requestHomeUrl + "?type=getpower&keyword=" + id3;
                            requestParams.setRequestUrl(requestUrl);
                            result = requestNet(requestParams);
                            result = result.replaceAll("\\\\", "");
                            result = result.substring(1, result.length() - 1);
                            // System.out.println(result);
                            json = JSON.parseObject(result);
                            JSONArray powerlist = json.getJSONArray("powerlist");
                            List<Map<String, Object>> powerList = new ArrayList<>();
                            if (powerlist.size() == 0) {
                                System.out.println("该发动机无用油信息，品牌id=" + webBrandId);
                            } else {
                                //循环油品列表
                                for (int x = 0; x < powerlist.size(); x++) {
                                    Map<String, Object> powerMap = (Map<String, Object>) powerlist.get(x);
                                    String descs = powerMap.get("descs").toString();//api 描述一
                                    String descs2 = powerMap.get("descs2").toString();//api 描述二
                                    String descs3 = powerMap.get("descs3").toString();//api 描述三
                                    String apiName = powerMap.get("apiName").toString();//api名字
                                    String identitys = powerMap.get("identitys").toString();//行驶距离


                                    String powerId = powerMap.get("id").toString();
                                    powerList.add(powerMap);
                                    fdjMap.put("powerInfo", powerList);
                                    requestUrl = requestHomeUrl + "?type=getapioil&keyword=" + powerId;
                                    requestParams.setRequestUrl(requestUrl);
                                    result = requestNet(requestParams);
                                    result = result.replaceAll("\\\\", "");
                                    result = result.substring(1, result.length() - 1);
                                    //  System.out.println(result);
                                    json = JSON.parseObject(result);
                                    JSONArray apioillist = json.getJSONArray("apioillist");

                                    if (apioillist.size() == 0) {
                                        System.out.print("无该油品图片");
                                    } else {
                                        String oilid = ((Map<String, String>) apioillist.get(0)).get("oilid").toString();
                                        String protectType = ((Map<String, String>) apioillist.get(0)).get("protectType");
                                        String protectTypeName = ((Map<String, String>) apioillist.get(0)).get("protectTypeName");
                                        requestUrl = requestHomeUrl + "?type=gettboil&diqu=undefined&keyword=" + oilid;
                                        requestParams.setRequestUrl(requestUrl);
                                        result = requestNet(requestParams);
                                        result = result.replaceAll("\\\\", "");
                                        try {
                                            result = result.substring(1, result.length() - 1);
                                            //System.out.println(result);
                                            json = JSON.parseObject(result);
                                            JSONArray oillist = json.getJSONArray("oillist");
                                            if (oillist.size() != 0) {
                                                String olisImageUrl = "http://47.105.96.134:8081/" + ((Map<String, Object>) oillist.get(0)).get("imgUrl").toString();//油品图片
                                                String olisSales = ((Map<String, Object>) oillist.get(0)).get("xiaoliang").toString();//油品销量
                                                String olisSae = ((Map<String, Object>) oillist.get(0)).get("SAE").toString();//油品SAE
                                                String olisPrice = ((Map<String, Object>) oillist.get(0)).get("price").toString();//油品价格
                                                String olisOrder = ((Map<String, Object>) oillist.get(0)).get("orders").toString();//油品排序
                                                String olisName = ((Map<String, Object>) oillist.get(0)).get("powerName").toString();//油品名字
                                                String olisKm = ((Map<String, Object>) oillist.get(0)).get("gonglishu").toString();//油品公里数

                                                if (olisMap.containsKey(olisName)) {
                                                    imgMaterial = olisMap.get(olisName);
                                                } else {
                                                    //保存油品信息
                                                    uploadFile = fileService.uploadFromWeb(olisImageUrl, appConfig.getOlis());
                                                    if (uploadFile == null)
                                                        uploadFile = fileService.uploadFromWeb(olisImageUrl, appConfig.getOlis());

                                                    imgMaterial = new ImgMaterial();
                                                    imgMaterial.setMaterialName(olisName);
                                                    imgMaterial.setMaterialKm(olisKm);
                                                    imgMaterial.setMaterialProtectedType(protectType);
                                                    imgMaterial.setMaterialType(2);
                                                    imgMaterial.setMaterialPrize(olisPrice);
                                                    if (StringUtil.isNotEmpty(olisOrder))
                                                        imgMaterial.setMaterialRank(Integer.parseInt(olisOrder));
                                                    imgMaterial.setMaterialSae(olisSae);
                                                    imgMaterial.setMaterialSales(olisSales);
                                                    imgMaterial.setUploadFile(uploadFile);

                                                    imgMaterial = imgMaterialRepository.save(imgMaterial);
                                                    imgBase = new ImgBase();
                                                    imgBase.setSystemType(brand.getSystemType());
                                                    imgBase.setImgMaterial(imgMaterial);
                                                    imgBase = imgBaseRepository.save(imgBase);
                                                    olisMap.put(olisName, imgMaterial);
                                                }


                                                //保存api列表
                                                SaeDesc saeDesc = new SaeDesc();
                                                saeDesc.setApiName(apiName);
                                                saeDesc.setRunKm(Integer.parseInt(identitys));
                                                saeDesc.setDesc1(descs);
                                                saeDesc.setDesc2(descs2);
                                                saeDesc.setDesc3(descs3);
                                                saeDesc.setThree(threeLevelCarType);
                                                saeDesc.setEngineType(engineType);
                                                saeDesc.setEngineId(engine.getId());
                                                saeDesc.setImgMaterial(imgMaterial);
                                                saeDescRepository.save(saeDesc);
                                                //System.out.print("品牌===>"+brand.getBrandName()+"保存完成\n");
                                                //System.out.print("详细===>"+brand.toString()+"=====\n");

                                                //imageByte=getImageFromNet("http://47.105.96.134:8081/"+imageUrl);
                                                //saveImageToLocahost("http://47.105.96.134:8081/"+imageUrl,3,imageName);

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static String requestNet(RequestParams requestParams) {
        URL realUrl = null;
        Random r = new Random();
        String[] ua = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
                "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};
        int i = r.nextInt(14);
        requestParams.setUserAgent(ua[i]);
        StringBuilder result = new StringBuilder();
        try {
            realUrl = new URL(requestParams.getRequestUrl());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", requestParams.getAccept());
            conn.setRequestProperty("connection", requestParams.getConnection());
            conn.setRequestProperty("user-agent", requestParams.getUserAgent());
            conn.setRequestProperty("referer", requestParams.getReferer());
            //conn.setConnectTimeout(10*1000);
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {
            requestNet(requestParams);
        } finally {

        }
        return result.toString();
    }


    @Data
    private class RequestParams {
        private String requestUrl;
        private String accept;
        private String connection;
        private String userAgent;
        private String referer;

        public RequestParams(String requestUrl, String accept, String connection, String userAgent, String referer) {
            this.requestUrl = requestUrl;
            this.accept = accept;
            this.connection = connection;
            this.userAgent = userAgent;
            this.referer = referer;
        }
    }
}
