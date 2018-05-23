ExecutorService es = Executors.newFixedThreadPool(10);
// ...
Callable<Integer> c = new Callable<>() { 
    public Integer call() {
      try { 
        Thread.sleep(2000L);
      } catch(InterruptedException e { }
      System.out.println("executor over");
      return 10;
    };
Future<Integer> f = es.submit(c);
int res = f.get();
