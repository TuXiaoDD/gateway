package disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.Sequence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("event {} sequence {} endOfBatch{}", event, sequence, endOfBatch);
    }

    @Override
    public void onBatchStart(long batchSize, long queueDepth) {
        EventHandler.super.onBatchStart(batchSize, queueDepth);
    }

    @Override
    public void onStart() {
        EventHandler.super.onStart();
    }

    @Override
    public void onShutdown() {
        EventHandler.super.onShutdown();
    }

    @Override
    public void onTimeout(long sequence) throws Exception {
        EventHandler.super.onTimeout(sequence);
    }

    @Override
    public void setSequenceCallback(Sequence sequenceCallback) {
        EventHandler.super.setSequenceCallback(sequenceCallback);
    }
}
