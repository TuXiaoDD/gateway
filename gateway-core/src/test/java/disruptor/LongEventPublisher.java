package disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventPublisher implements EventTranslator<LongEvent> {

//    private RingBuffer<LongEvent> ringBuffer;
//
//    public LongEventPublisher(RingBuffer<LongEvent> ringBuffer){
//        this.ringBuffer = ringBuffer;
//    }

    @Override
    public void translateTo(LongEvent event, long sequence) {
        log.info("LongEventPublisher translateTo event {} sequence {}",event,sequence);



    }
}
