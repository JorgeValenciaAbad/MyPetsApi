package it.jorge.protectora.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.RequestAdoption;
import it.jorge.protectora.Model.User;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static it.jorge.protectora.util.Constants.requestRoute;
import static it.jorge.protectora.util.Constants.route;

public class Functions {
    private static final String SECRET = "mySecretKey";
    public static String getJWTToken(User user) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        Claims claims = Jwts.claims();
        claims.put("id",user.getId());

        return Jwts
                .builder()
                .setId("JWT")
                .setClaims(claims)
                .setSubject("USER TOKEN")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
    public static String encodePass (String pass){

        return new BCryptPasswordEncoder().encode(pass);
    }
    public static Claims getClaims (String token){
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
    }

    public static boolean validateDniNie(String dniNie) {
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        String dniNieTrim = dniNie.trim().toUpperCase();

        if (dniNieTrim.length() != 9 || !Character.isLetter(dniNieTrim.charAt(8))) {
            return false;
        }

        char verifyLetter = letters.charAt(Integer.parseInt(dniNieTrim.substring(0, 8)) % 23);

        return dniNieTrim.charAt(8) == verifyLetter;
    }
    public static boolean validateDate(String bornDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bornDateParse = LocalDate.parse(bornDate, format);
        LocalDate today = LocalDate.now();
        int age = Period.between(bornDateParse, today).getYears();

        return age > 18;
    }
    public static void InformGenerate(RequestAdoption request, Pet pet){

        String documentName = "adoption-request-"+request.getName()+request.getSecondName()+"-"+pet.getName()+".pdf";

        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(requestRoute+"//"+documentName));
            document.open();

            Paragraph title = new Paragraph("Adoption Request");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph space = new Paragraph("");
            document.add(space);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Name:");
            table.addCell(request.getName() + " " + request.getSecondName());
            table.addCell("DNI o NIF:");
            table.addCell(request.getIdentification());
            table.addCell("Phone:");
            table.addCell(request.getPhone());
            table.addCell("Email:");
            table.addCell(request.getEmail());
            table.addCell("Born date:");
            table.addCell(request.getBornDate());
            table.addCell("Address:");
            table.addCell(request.getAddress());
            table.addCell("Country:");
            table.addCell(request.getCountry());
            table.addCell("Region:");
            table.addCell(request.getRegion());
            table.addCell("Home type:");
            table.addCell(request.getTypeHome());
            table.addCell("Home:");
            table.addCell(request.getHome());
            table.addCell("Home surface:");
            table.addCell(request.getSurface());
            table.addCell("Kids:");
            if(request.getKids()){
                table.addCell("Yes");
            }else{
                table.addCell("No");
            }
            table.addCell("Pets:");
            if(request.getPets()){
                table.addCell("Yes");
            }else{
                table.addCell("No");
            }
            document.add(table);
            document.close();

           // Files.copy( new File(documentName).toPath(), new File(requestRoute).toPath(), StandardCopyOption.REPLACE_EXISTING);

            String host = "smtp.gmail.com";
            int puerto = 587;
            String user = "noreplymypets@gmail.com";
            String pass = "hdncjftxzgxxkeaz";

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", puerto);
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getEmail()));
            message.setSubject("Adoption Request");
            message.setText("we attach the adoption request.");
            MimeBodyPart attached = new MimeBodyPart();
            attached.attachFile(new File(requestRoute+"//"+documentName));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attached);
            message.setContent(multipart);

            Transport.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

