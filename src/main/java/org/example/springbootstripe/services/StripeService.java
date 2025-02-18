package org.example.springbootstripe.services;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    public StripeService() {
    }

    public String createCharge(String email, String token, double amount, String competition, String dataInici, String dataFi) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();

            // Convertir la cantidad a centavos y redondearla a un número entero
            int amountInCents = (int) (amount * 100);  // Multiplicar por 100 para obtener euros

            chargeParams.put("amount", amountInCents);
            chargeParams.put("currency", "eur");
            chargeParams.put("description", "Charge for " + email + " for " + competition + " from " + dataInici + " to " + dataFi);
            chargeParams.put("source", token);
            chargeParams.put("receipt_email", email);
            chargeParams.put("metadata", new HashMap<String, String>() {{
                put("email", email);
                put("competition", competition);
                put("dataInici", dataInici);
                put("dataFi", dataFi);
            }});

            // Crear un cargo
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
