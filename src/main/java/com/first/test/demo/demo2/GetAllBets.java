package com.first.test.demo.demo2;

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAllBets {


    public int getPlayerRoundBets(BigInteger pid, BigInteger rid) throws IOException {
        Function function = new Function("getPlayerRoundBets", Arrays.asList(new Uint(rid), new Uint(pid)), Arrays.asList(
                new TypeReference<DynamicArray<Uint>>() {
                }));
        String result = TransTest.query(null, "0x379ebb850cb30cb5e50a351b6da437a00eaadc20", function);

        List<Type> types = FunctionReturnDecoder.decode(result, function.getOutputParameters());

        List<Uint> list = (List<Uint>) types.get(0).getValue();
        List<Long> roundBitList = new ArrayList<>(list.size());
        list.stream().forEach(item -> roundBitList.add(item.getValue().longValue()));
        System.out.println("ThreadName=" + Thread.currentThread() + "roundBitList.size()=" + roundBitList.size());
        return roundBitList.size();
    }

    public BigInteger[] allStaticProfits(BigInteger pid, BigInteger rid) throws IOException {
        Function function = new Function("allStaticProfits", Arrays.asList(new Uint(rid), new Uint(pid)), Arrays.asList(
                new TypeReference<DynamicArray<Uint>>() {
                }));
        String result = TransTest.query(null, "0x379ebb850cb30cb5e50a351b6da437a00eaadc20", function);
        String result1 = result.substring(2);
        BigInteger result2 = new BigInteger(result1, 16);
        BigInteger num1 = new BigInteger("1000000000000000000");
        BigInteger[] none = result2.divideAndRemainder(num1);
        return none;

    }


}
