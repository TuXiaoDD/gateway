package disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {


    public static void main(String[] args) {
        LongEventFactory factory = new LongEventFactory();
        int ringBufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,
                ringBufferSize,
                Thread::new,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        disruptor.handleEventsWith(new LongEventHandler());

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventPublisher eventTranslator = new LongEventPublisher();

        for (long i = 0; i < 100; i++) {
            long next = ringBuffer.next();
            eventTranslator.translateTo(new LongEvent(i), next);
        }

    }
}
