package tw.meowdev.android.starter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import tw.meowdev.android.starter.list.CircularLzList;
import tw.meowdev.android.starter.list.CircularLzListAdapter;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private CircularLzList<String> lzList;
    private CircularLzListAdapter<String> lzListAdapter;
    private final NumGenerator generator = new NumGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView)findViewById(R.id.listView);

        lzList = new CircularLzList<String>(100);
        for(int i=0; i < 20; i++)
            lzList.append(generator.getNum(i)+"");

        lzListAdapter = new CircularLzListAdapter<String>(this, lzList);
        listView.setAdapter(lzListAdapter);
        listView.setOnScrollListener(scrollListener);
    }

    private Handler handler = new Handler();

    private void fetchMore(int start, int amount) {
        for(int i=start; i < start+amount; i++)
            lzList.append(generator.getNum(i)+"");
    }

    private void fetchPrev(int start, int amount) {
        for(int i=start; i > start-amount && i >= 0; i--)
            lzList.push(generator.getNum(i) + "");
    }

    ListView.OnScrollListener scrollListener = new ListView.OnScrollListener() {
        private long preLast;
        private long preFirst;
        private boolean fetching = false;

        @Override
        public void onScrollStateChanged(AbsListView lw, final int index)
        {
        }

        @Override
        public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;

            if(lastItem == totalItemCount && !fetching) {
                long glastItem = lzListAdapter.getGlobalIndex(lastItem);
                if (preLast != glastItem) { //to avoid multiple calls for last item
                    fetching = true;
                    preLast = glastItem;

                    final int last = lzList.start() + lzList.size();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchMore(last, 10);

                            lzListAdapter.notifyDataSetChanged();
                            if (totalItemCount == 100)
                                listView.setSelection(totalItemCount - visibleItemCount - 10 + 2);
                            fetching = false;
                            preFirst = -1;
                        }
                    }, 500);
                }
            } else if(firstVisibleItem == 0 && !fetching) {
                final long gFirstItem = lzListAdapter.getGlobalIndex(firstVisibleItem);
                if (preFirst != gFirstItem && gFirstItem > 0) { //to avoid multiple calls for last item
                    fetching = true;
                    preFirst = gFirstItem;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchPrev((int)gFirstItem-1, 10);

                            lzListAdapter.notifyDataSetChanged();
                            listView.setSelection((int) firstVisibleItem + 10);
                            fetching = false;
                            preLast = -1;
                        }
                    }, 500);
                }
            }
        }
    };

    private class NumGenerator {
        public long getNum(int index) {
            return (long)index;
        }
    }
}
