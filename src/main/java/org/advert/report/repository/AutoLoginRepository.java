package org.advert.report.repository;

import org.advert.report.bean.AutoLogin;
import org.advert.report.util.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by shiqm on 2018-06-13.
 */

@Repository
public class AutoLoginRepository extends MongoRepository<AutoLogin,String>{

}
