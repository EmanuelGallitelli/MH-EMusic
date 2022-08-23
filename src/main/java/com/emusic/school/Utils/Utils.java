package com.emusic.school.Utils;

import net.bytebuddy.utility.RandomString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    private static List<String> tokensCreated = new ArrayList<>();

    public static String generateToken (int tokenL){
        String token = " ";
        do {token = RandomString.make(tokenL);
        }while (tokensCreated.contains(token));
            tokensCreated.add(token);
            return token;
    }

    public static void deleteToken(String tokenD){
        tokensCreated = tokensCreated.stream().filter(token -> token != tokenD).collect(Collectors.toList());
    }


}
