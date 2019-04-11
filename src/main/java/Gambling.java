import domain.Console;
import domain.State;
import lotto.BuyLotto;
import lotto.Lotto;
import lotto.Rank;
import lotto.WinningLotto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Gambling {
    private Console console;

    Gambling() {
        console = new Console();
    }

    void start() throws IOException {
        List<Lotto> lottos = makeLottos();
        WinningLotto winningLotto = makeWinningLotto();
        List<Rank> resultRanks = makeMatchResult(lottos, winningLotto);
        console.writeResult(new State().statsLottoResult(resultRanks));
    }

    private List<Lotto> makeLottos() throws IOException {
        int money = console.readMoney();
        List<Lotto> lottos = new BuyLotto().buying(money);
        console.writeLottos(lottos);

        return lottos;
    }

    private WinningLotto makeWinningLotto() throws IOException {
        Lotto winningNumber = winningNumber();
        int bonusNumber = console.readBonusNumber();

        return new WinningLotto(winningNumber, bonusNumber);
    }

    private Lotto winningNumber() throws IOException {
        return new Lotto(Arrays.stream(console.readWinningNumbers())
                .boxed()
                .collect(Collectors.toList()));
    }

    private List<Rank> makeMatchResult(List<Lotto> lottos, WinningLotto winningLotto) {
        List<Rank> ranks = new ArrayList<>();
        lottos.forEach(lotto -> ranks.add(winningLotto.match(lotto)));

        return ranks;
    }
}
