package iam.solucoesdigitais.baseappjwt.util;

import org.springframework.stereotype.Service;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

@Service
public class EmailValidatorService {

    public boolean isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            Record[] records = new Lookup(domain, Type.MX).run();
            return records != null && records.length > 0 && records[0] instanceof MXRecord;
        } catch (Exception e) {
            return false;
        }
    }
}
