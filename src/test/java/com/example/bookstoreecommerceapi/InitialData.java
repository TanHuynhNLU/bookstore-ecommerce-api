package com.example.bookstoreecommerceapi;

import com.example.bookstoreecommerceapi.dto.OrderDetailRequest;
import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.models.Contact;
import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.repositories.UserRepository;
import com.example.bookstoreecommerceapi.services.ContactService;
import com.example.bookstoreecommerceapi.services.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class InitialData {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void initialUsers() {
        User user1 = User.builder()
                .fullName("Nguyễn Văn Hoàng")
                .username("nvhoang")
                .password(passwordEncoder.encode("12345678"))
                .email("nvhoang@gmail.com")
                .role("USER")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar1.png")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .fullName("Nguyễn Trung Hậu")
                .username("nthau")
                .password(passwordEncoder.encode("12345678"))
                .email("nthau@gmail.com")
                .role("USER")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar2.png")
                .build();
        userRepository.save(user2);

        User user3 = User.builder()
                .fullName("Nguyễn Văn Quyết")
                .username("nvquyet")
                .password(passwordEncoder.encode("12345678"))
                .email("nvquyet@gmail.com")
                .role("USER")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar3.png")
                .build();
        userRepository.save(user3);

        User user4 = User.builder()
                .fullName("Hồng Văn Sơn")
                .username("hvson")
                .password(passwordEncoder.encode("12345678"))
                .email("hvson@gmail.com")
                .role("USER")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar4.png")
                .build();
        userRepository.save(user4);

        User user5 = User.builder()
                .fullName("Nguyễn Huy Chấn")
                .username("nhchan")
                .password(passwordEncoder.encode("12345678"))
                .email("nhchan@gmail.com")
                .role("USER")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar5.png")
                .build();
        userRepository.save(user5);

        User user6 = User.builder()
                .fullName("Huỳnh Ngọc Tấn")
                .username("tanhuynh")
                .password(passwordEncoder.encode("tanhuynh"))
                .email("tanhuynh@gmail.com")
                .role("ADMIN")
                .gender("Nam")
                .address("TPHCM")
                .phone("1324657989")
                .dateRegistered(new Date())
                .status("Kích hoạt")
                .avatar("http://localhost:8080/api/FileUpload/files/avatar5.png")
                .build();
        userRepository.save(user6);
    }

    @Test
    public void initialBooks() {
        Book book1 = Book.builder()
                .name("Nhà Giả Kim")
                .genre("Tiểu thuyết")
                .stock(2548)
                .price(63200)
                .sales(200)
                .author("Paulo Coelho")
                .publisher("NXB Hội Nhà Văn")
                .numberOfPage(227)
                .published(2020)
                .status("Mở bán")
                .description("Tất cả những trải nghiệm trong chuyến phiêu du theo đuổi vận mệnh của mình đã giúp Santiago thấu hiểu được ý nghĩa sâu xa nhất của hạnh phúc, hòa hợp với vũ trụ và con người. \n" +
                        "\n" +
                        "Tiểu thuyết Nhà giả kim của Paulo Coelho như một câu chuyện cổ tích giản dị, nhân ái, giàu chất thơ, thấm đẫm những minh triết huyền bí của phương Đông. Trong lần xuất bản đầu tiên tại Brazil vào năm 1988, sách chỉ bán được 900 bản. Nhưng, với số phận đặc biệt của cuốn sách dành cho toàn nhân loại, vượt ra ngoài biên giới quốc gia, Nhà giả kim đã làm rung động hàng triệu tâm hồn, trở thành một trong những cuốn sách bán chạy nhất mọi thời đại, và có thể làm thay đổi cuộc đời người đọc.\n" +
                        "\n" +
                        "“Nhưng nhà luyện kim đan không quan tâm mấy đến những điều ấy. Ông đã từng thấy nhiều người đến rồi đi, trong khi ốc đảo và sa mạc vẫn là ốc đảo và sa mạc. Ông đã thấy vua chúa và kẻ ăn xin đi qua biển cát này, cái biển cát thường xuyên thay hình đổi dạng vì gió thổi nhưng vẫn mãi mãi là biển cát mà ông đã biết từ thuở nhỏ. Tuy vậy, tự đáy lòng mình, ông không thể không cảm thấy vui trước hạnh phúc của mỗi người lữ khách, sau bao ngày chỉ có cát vàng với trời xanh nay được thấy chà là xanh tươi hiện ra trước mắt. ‘Có thể Thượng đế tạo ra sa mạc chỉ để cho con người biết quý trọng cây chà là,’ ông nghĩ.”\n" +
                        "\n" +
                        "- Trích Nhà giả kim")
                .image("http://localhost:8080/api/FileUpload/files/nha-gia-kim.jpg")
                .build();
        bookRepository.save(book1);
        Book book2 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .genre("Tiểu thuyết")
                .stock(1596)
                .price(63200)
                .sales(142)
                .author("Luis Sepúlveda")
                .publisher("NXB Hội Nhà Văn")
                .numberOfPage(144)
                .published(2019)
                .status("Mở bán")
                .description("Cô hải âu Kengah bị nhấn chìm trong váng dầu – thứ chất thải nguy hiểm mà những con người xấu xa bí mật đổ ra đại dương. Với nỗ lực đầy tuyệt vọng, cô bay vào bến cảng Hamburg và rơi xuống ban công của con mèo mun, to đùng, mập ú Zorba. Trong phút cuối cuộc đời, cô sinh ra một quả trứng và con mèo mun hứa với cô sẽ thực hiện ba lời hứa chừng như không tưởng với loài mèo:\n" +
                        "\n" +
                        "Không ăn quả trứng.\n" +
                        "Chăm sóc cho tới khi nó nở.\n" +
                        "Dạy cho con hải âu bay.\n" +
                        "\n" +
                        "Lời hứa của một con mèo cũng là trách nhiệm của toàn bộ mèo trên bến cảng, bởi vậy bè bạn của Zorba bao gồm ngài mèo Đại Tá đầy uy tín, mèo Secretario nhanh nhảu, mèo Einstein uyên bác, mèo Bốn Biển đầy kinh nghiệm đã chung sức giúp nó hoàn thành trách nhiệm. Tuy nhiên, việc chăm sóc, dạy dỗ một con hải âu đâu phải chuyện đùa, sẽ có hàng trăm rắc rối nảy sinh và cần có những kế hoạch đầy linh hoạt được bàn bạc kỹ càng…\n" +
                        "\n" +
                        "Chuyện con mèo dạy hải âu bay là kiệt tác dành cho thiếu nhi của nhà văn Chi Lê nổi tiếng Luis Sepúlveda – tác giả của cuốn Lão già mê đọc truyện tình đã bán được 18 triệu bản khắp thế giới. Tác phẩm không chỉ là một câu chuyện ấm áp, trong sáng, dễ thương về loài vật mà còn chuyển tải thông điệp về trách nhiệm với môi trường, về sự sẻ chia và yêu thương cũng như ý nghĩa của những nỗ lực – “Chỉ những kẻ dám mới có thể bay”.")
                .image("http://localhost:8080/api/FileUpload/files/chuyen-con-meo-day-hai-au-bay.jpg")
                .build();
        bookRepository.save(book2);
        Book book3 = Book.builder()
                .name("Cây Cam Ngọt Của Tôi")
                .genre("Tiểu thuyết")
                .stock(1762)
                .price(75600)
                .sales(121)
                .author("José Mauro de Vasconcelos")
                .publisher("NXB Hội Nhà Văn")
                .numberOfPage(244)
                .published(2020)
                .status("Mở bán")
                .description("Hãy làm quen với Zezé, cậu bé tinh nghịch siêu hạng đồng thời cũng đáng yêu bậc nhất, với ước mơ lớn lên trở thành nhà thơ cổ thắt nơ bướm. Chẳng phải ai cũng công nhận khoản “đáng yêu” kia đâu nhé. Bởi vì, ở cái xóm ngoại ô nghèo ấy, nỗi khắc khổ bủa vây đã che mờ mắt người ta trước trái tim thiện lương cùng trí tưởng tượng tuyệt vời của cậu bé con năm tuổi.\n" +
                        "\n" +
                        "Có hề gì đâu bao nhiêu là hắt hủi, đánh mắng, vì Zezé đã có một người bạn đặc biệt để trút nỗi lòng: cây cam ngọt nơi vườn sau. Và cả một người bạn nữa, bằng xương bằng thịt, một ngày kia xuất hiện, cho cậu bé nhạy cảm khôn sớm biết thế nào là trìu mến, thế nào là nỗi đau, và mãi mãi thay đổi cuộc đời cậu.\n" +
                        "\n" +
                        "Mở đầu bằng những thanh âm trong sáng và kết thúc lắng lại trong những nốt trầm hoài niệm, Cây cam ngọt của tôi khiến ta nhận ra vẻ đẹp thực sự của cuộc sống đến từ những điều giản dị như bông hoa trắng của cái cây sau nhà, và rằng cuộc đời thật khốn khổ nếu thiếu đi lòng yêu thương và niềm trắc ẩn. Cuốn sách kinh điển này bởi thế không ngừng khiến trái tim người đọc khắp thế giới thổn thức, kể từ khi ra mắt lần đầu năm 1968 tại Brazil.")
                .image("http://localhost:8080/api/FileUpload/files/cay-cam-ngot-cua-toi.jpg")
                .build();
        bookRepository.save(book3);
        Book book4 = Book.builder()
                .name("Sưởi Ấm Mặt Trời")
                .genre("Tiểu thuyết")
                .stock(1200)
                .price(128000)
                .sales(352)
                .author("José Mauro de Vasconcelos")
                .publisher("NXB Hội Nhà Văn")
                .numberOfPage(244)
                .published(2020)
                .status("Mở bán")
                .description("Zezé, cậu bé tinh nghịch siêu hạng đồng thời cũng đáng yêu bậc nhất ngày ngào giờ đã thoát khỏi cuộc sống nghèo khó, cũng không còn phải chịu cảnh bị đánh đập thường xuyên như trong quá khứ. Cậu đã chuyển đến Natal sống cùng gia đình cha đỡ đầu, được học ở một ngôi trường tốt, dần dần tiến bộ cả về mặt trí tuệ lẫn thể chất. Nhưng nỗi đau mất mát vẫn đè nặng lên trái tim cậu và Zezé vẫn là một cậu nhóc “ hầu như lúc nào cũng buồn”, thậm chí “có lẽ là một trong những cậu nhóc buồn nhất quả đất”.\n" +
                        "\n" +
                        "Nhưng, may thay, Zezé đã tìm được những người bạn mới – cả ở đời thực lẫn trong tưởng tượng – luôn thấu hiểu và sát cánh bên cậu, cùng cậu đi qua hết thày những niềm vui cùng nỗi buồn, những khổ sở, thất vọng, những phiêu lưu mạo hiểm, giúp cậu đối thoại với cuộc sống muôn màu muôn vẻ, với nội tâm đầy mâu thuẫn và đồng thời với cả nỗi buồn thương sâu thẳm trong tâm hồn.\n" +
                        "\n" +
                        "Sâu lắng, day dứt và có những khi buồn đến thắt lòng, nhưng đồng thời, Sưởi ấm mặt trời cũng tràn ngập hơi thở trẻ trung, trong sáng, tràn đầy sức sống và tình yêu thương.")
                .image("http://localhost:8080/api/FileUpload/files/suoi-am-mat-troi.jpg")
                .build();
        bookRepository.save(book4);
        Book book5 = Book.builder()
                .name("Dune - Xứ Cát")
                .genre("Tiểu thuyết")
                .stock(1235)
                .price(161850)
                .sales(145)
                .author("Frank Herbert")
                .publisher("NXB Hội Nhà Văn")
                .numberOfPage(714)
                .published(2020)
                .status("Mở bán")
                .description("Một thời điểm rất xa trong tương lai…\n" +
                        "\n" +
                        "Từ đời này sang đời khác, người Fremen trên hành tinh sa mạc lưu truyền lời tiên tri về một đấng cứu tinh sẽ dẫn dắt họ giành lấy tự do đích thực…\n" +
                        "\n" +
                        "Từ thế hệ này sang thế hệ khác, những nữ phù thủy Bene Gesserit mỏi mòn chờ đợi sự xuất hiện của một B.G. nam giới duy nhất, người có thể vượt qua mọi giới hạn không gian - thời gian…\n" +
                        "\n" +
                        "Là Lisal al-Gaib của người Fremen, là Kwisatz Haderach của học viện Bene Gesserit, chàng công tước trẻ tuổi Paul Atreides đã làm tất cả những gì có thể để thay đổi định mệnh đó. Cha bị giết chết, mẹ bị cho là kẻ phản bội, gia tộc bị tàn sát, bản thân bị săn đuổi đến đường cùng, Paul đơn độc dấn thân vào một cuộc phiêu lưu sinh tử, không hề biết rằng mỗi hành động của mình sẽ góp phần quyết định vận mệnh của cả thiên hà. Sa mạc Arrakis khắc nghiệt tưởng như sẽ là nơi chôn vùi vĩnh viễn vinh quang của gia tộc Atreides, nhưng hóa ra lại thành điểm khởi đầu cho một huyền thoại mới…")
                .image("http://localhost:8080/api/FileUpload/files/dune-xu-cat.jpg")
                .build();
        bookRepository.save(book5);
        Book book6 = Book.builder()
                .name("Con Đường Chẳng Mấy Ai Đi")
                .genre("Kỹ Năng Sống")
                .stock(2250)
                .price(112200)
                .sales(556)
                .author("M. Scott Peck")
                .publisher("NXB Dân Trí")
                .numberOfPage(344)
                .published(2024)
                .status("Mở bán")
                .description("Có lẽ không quyển sách nào trong thế kỷ này có tác động sâu sắc đến đời sống trí tuệ và tinh thần của chúng ta hơn Con Đường Chẳng Mấy Ai Đi. Với doanh số trên 10 triệu bản in trên toàn thế giới và được dịch sang hơn 25 ngôn ngữ, đây là một hiện tượng trong ngành xuất bản, với hơn mười năm nằm trong danh sách Best-sellers của New York Times.\n" +
                        "\n" +
                        "Với cách hành văn kinh điển và thông điệp đầy thấu hiểu, quyển sách Con Đường Chẳng Mấy Ai Đi giúp chúng ta khám phá bản chất của các mối quan hệ và của một tinh thần trưởng thành. Quyển sách giúp chúng ta học cách phân biệt sự lệ thuộc với tình yêu; làm thế nào để trở thành những bậc phụ huynh tinh tế và nhạy cảm; và cuối cùng là làm thế nào để sống chân thật với chính mình.\n" +
                        "\n" +
                        "Với dòng mở đầu bất hủ của quyển sách, \"Cuộc đời này rất khó sống\", thể hiện quan điểm hành trình phát triển tinh thần là một chặng đường dài và gian nan, Tiến sĩ Peck thể hiện sự đồng cảm, nhẹ nhàng dẫn dắt độc giả vượt qua quá trình khó khăn đó, để thay đổi hướng tới tầm mức thấu hiểu bản thân sâu sắc hơn.")
                .image("http://localhost:8080/api/FileUpload/files/con-duong-chang-may-ai-di.jpg")
                .build();
        bookRepository.save(book6);
        Book book7 = Book.builder()
                .name("Flow - Dòng Chảy")
                .genre("Kỹ Năng Sống")
                .stock(3500)
                .price(159600)
                .sales(215)
                .author("Mihaly Csikszentmihalyi")
                .publisher("NXB Dân Trí")
                .numberOfPage(552)
                .published(2021)
                .status("Mở bán")
                .description("Dành cho những ai muốn tối ưu khả năng tập trung để tìm kiếm sự gắn kết trong công việc, hướng đến sự viên mãn, và hạnh phúc trong mọi trải nghiệm cuộc sống\n" +
                        "\n" +
                        "Có bao giờ bạn hoàn toàn chìm đắm vào một cuốn sách hay, một công việc, hay một buổi trình diễn đến mức không nhận thấy hàng giờ đồng hồ đã trôi qua, thậm chí bạn chẳng có ý niệm gì về mọi thứ xung quanh hay sự tồn tại của chính mình? Thời khắc ấy, một sự khoan khoái kỳ lạ tuôn chảy trong con người bạn, đến mức bạn không ngừng tìm kiếm để có lại những cảm giác tương tự, nhưng điều đó dường như là không thể, bởi bạn chưa thật sự hiểu cảm giác đó là gì, cũng như nguồn gốc hình thành nên trải nghiệm tuyệt vời ấy.")
                .image("http://localhost:8080/api/FileUpload/files/flow-dong-chay.jpg")
                .build();
        bookRepository.save(book7);
        Book book8 = Book.builder()
                .name("Hiểu Về Trái Tim")
                .genre("Kỹ Năng Sống")
                .stock(558)
                .price(126400)
                .sales(150)
                .author("Minh Niệm")
                .publisher("NXB Tổng Hợp TPHCM")
                .numberOfPage(479)
                .published(2023)
                .status("Mở bán")
                .description("“Hiểu về trái tim” là một cuốn sách đặc biệt được viết bởi thiền sư Minh Niệm. Với phong thái và lối hành văn gần gũi với những sinh hoạt của người Việt, thầy Minh Niệm đã thật sự thổi hồn Việt vào cuốn sách nhỏ này.\n" +
                        "\n" +
                        "Xuất bản lần đầu tiên vào năm 2011, Hiểu Về Trái Tim trình làng cũng lúc với kỷ lục: cuốn sách có số lượng in lần đầu lớn nhất: 100.000 bản. Trung tâm sách kỷ lục Việt Nam công nhận kỳ tích ấy nhưng đến nay, con số phát hành của Hiểu về trái tim vẫn chưa có dấu hiệu chậm lại.\n" +
                        "\n" +
                        "Là tác phẩm đầu tay của nhà sư Minh Niệm, người sáng lập dòng thiền hiểu biết (Understanding Meditation), kết hợp giữa tư tưởng Phật giáo Đại thừa và Thiền nguyên thủy Vipassana, nhưng Hiểu Về Trái Tim không phải tác phẩm thuyết giáo về Phật pháp. Cuốn sách rất “đời” với những ưu tư của một người tu nhìn về cõi thế. Ở đó, có hạnh phúc, có đau khổ, có tình yêu, có cô đơn, có tuyệt vọng, có lười biếng, có yếu đuối, có buông xả… Nhưng, tất cả những hỉ nộ ái ố ấy đều được khoác lên tấm áo mới, một tấm áo tinh khiết và xuyên suốt, khiến người đọc khi nhìn vào, đều thấy mọi sự như nhẹ nhàng hơn…")
                .image("http://localhost:8080/api/FileUpload/files/hieu-ve-trai-tim.jpg")
                .build();
        bookRepository.save(book8);
        Book book9 = Book.builder()
                .name("Không Phải Sói Nhưng Cũng Đừng Là Cừu")
                .genre("Kỹ Năng Sống")
                .stock(789)
                .price(99840)
                .sales(145)
                .author("Lê Bảo Ngọc")
                .publisher("NXB Thế Giới")
                .numberOfPage(296)
                .published(2022)
                .status("Mở bán")
                .description("Không Phải Sói Nhưng Cũng Đừng Là Cừu\n" +
                        "\n" +
                        "SÓI VÀ CỪU - BẠN KHÔNG TỐT NHƯ BẠN NGHĨ ĐÂU!\n" +
                        "\n" +
                        "Làn ranh của việc ngây thơ hay xấu xa đôi khi mỏng manh hơn bạn nghĩ.\n" +
                        "\n" +
                        "Bạn làm một việc mà mình cho là đúng, kết quả lại bị mọi người khiển trách.\n" +
                        "\n" +
                        "Bạn ủng hộ một quan điểm của ai đó, và số đông khác lại ủng hộ một quan điểm trái chiều.\n" +
                        "\n" +
                        "Rốt cuộc thì bạn sai hay họ sai?\n" +
                        "\n" +
                        "Cuốn sách “Không phải sói nhưng cũng đừng là cừu” đến từ tác giả Lê Bảo Ngọc sẽ giúp bạn hiểu rõ hơn những khía cạnh sắc sảo phía sau những nhận định đúng, sai đơn thuần của mỗi người.\n" +
                        "\n" +
                        "Những câu hỏi đầy tính tranh cãi như “Cứu người hay cứu chó?”, “Một kẻ thô lỗ trong lớp vỏ “thẳng tính” khác với người EQ thấp như thế nào?”, “Vì sao một bộ phận nữ giới lại victim blaming đối với nạn nhân bị xâm hại?”,... được tác giả đưa ra trong “Không phải sói nhưng cũng đừng là cừu”. Bằng từng chương sách của mình, tác giả vạch rõ cho bạn rằng “thật sự thế nào mới là người tốt”, ngây thơ và xấu xa có ranh giới rõ ràng như thế không, rốt cuộc bạn có là người tốt như mình vẫn luôn nghĩ?")
                .image("http://localhost:8080/api/FileUpload/files/khong-phai-soi-nhung-cung-dung-la-cuu.jpg")
                .build();
        bookRepository.save(book9);
        Book book10 = Book.builder()
                .name("Đi Tìm Lẽ Sống")
                .genre("Kỹ Năng Sống")
                .stock(1256)
                .price(61600)
                .sales(253)
                .author("Viktor E Frankl")
                .publisher("NXB Thế Giới")
                .numberOfPage(224)
                .published(2022)
                .status("Mở bán")
                .description("Thông thường, nếu một quyển sách chỉ có một đoạn văn, một ý tưởng có sức mạnh thay đổi cuộc sống của một người, thì chỉ riêng điều đó cũng đã đủ để chúng ta đọc đi đọc lại và dành cho nó một chỗ trên kệ sách của mình. Quyển sách này có nhiều đoạn văn như thế.\n" +
                        "\n" +
                        "Trước hết, đây là quyển sách viết về sự sinh tồn. Giống như nhiều người Do Thái sinh sống tại Đức và Đông Âu khi ấy, vốn nghĩ rằng mình sẽ được an toàn trong những năm 1930, Frankl đã trải qua khoảng thời gian chịu nhiều nghiệt ngã trong trại tập trung và trại hủy diệt của Đức quốc xã. Điều kỳ diệu là ông đã sống sót, cụm từ “thép đã tôi thế đấy” có thể lột tả chính xác trường hợp này. Nhưng trong Đi tìm lẽ sống, tác giả ít đề cập đến những khó nhọc, đau thương, mất mát mà ông đã trải qua, thay vào đó ông viết về những nguồn sức mạnh đã giúp ông tồn tại.")
                .image("http://localhost:8080/api/FileUpload/files/di-tim-le-song.jpg")
                .build();
        bookRepository.save(book10);
        Book book11 = Book.builder()
                .name("48 Nguyên Tắc Chủ Chốt Của Quyền Lực")
                .genre("Kinh Tế")
                .stock(2256)
                .price(164000)
                .sales(1250)
                .author("Robert Greene")
                .publisher("NXB Trẻ")
                .numberOfPage(504)
                .published(2020)
                .status("Mở bán")
                .description("Quyền lực có sức hấp dẫn vô cùng mạnh mẽ đối với con người trong mọi thời, ở mọi nơi, với mọi giai tầng. Lịch sử xét cho cùng là cuộc đấu tranh triền miên để giành cho bằng được quyền lực cai trị của các tập đoàn thống trị, từ cổ chí kim, từ đông sang tây.\n" +
                        "\n" +
                        "Quyền lực là thứ mà rất nhiều người mong muốn nhưng không phải ai cũng dễ dàng đạt được. Vươn lên những vị trí cao hơn trong thang bậc xã hội thường được xem là một khát khao rất con người. Nhưng, liệu có phải chỉ những tài năng xuất chúng mới có thể đạt được điều đó? Không hẳn vậy. Bởi ít ai biết rằng, để giành được một vị trí quyền lực thực tế vẫn mang tính công thức.")
                .image("http://localhost:8080/api/FileUpload/files/48-nguyen-tac-chu-chot-cua-quyen-luc.jpg")
                .build();
        bookRepository.save(book11);
        Book book12 = Book.builder()
                .name("Nghĩ Giàu & Làm Giàu")
                .genre("Kinh Tế")
                .stock(1236)
                .price(77000)
                .sales(258)
                .author("Napoleon Hill")
                .publisher("NXB Tổng Hợp TPHCM")
                .numberOfPage(443)
                .published(2020)
                .status("Mở bán")
                .description("Think and Grow Rich - Nghĩ giàu và làm giàu là một trong những cuốn sách bán chạy nhất mọi thời đại. Đã hơn 60 triệu bản được phát hành với gần trăm ngôn ngữ trên toàn thế giới và được công nhận là cuốn sách tạo ra nhiều triệu phú, một cuốn sách truyền cảm hứng thành công nhiều hơn bất cứ cuốn sách kinh doanh nào trong lịch sử.\n" +
                        "\n" +
                        "Tác phẩm này đã giúp tác giả của nó, Napoleon Hill, được tôn vinh bằng danh hiệu “người tạo ra những nhà triệu phú”. Đây cũng là cuốn sách hiếm hoi được đứng trong top của rất nhiều bình chọn theo nhiều tiêu chí khác nhau - bình chọn của độc giả, của giới chuyên môn, của báo chí. Lý do để Think and Grow Rich - Nghĩ giàu và làm giàu có được vinh quang này thật hiển nhiên và dễ hiểu: Bằng việc đọc và áp dụng những phương pháp đơn giản, cô đọng này vào đời sống của mỗi cá nhân mà đã có hàng ngàn người trên thế giới trở thành triệu phú và thành công bền vững.")
                .image("http://localhost:8080/api/FileUpload/files/nghi-giau-lam-giau.jpg")
                .build();
        bookRepository.save(book12);
        Book book13 = Book.builder()
                .name("Những Kẻ Xuất Chúng")
                .genre("Kinh Tế")
                .stock(1524)
                .price(119250)
                .sales(526)
                .author("Malcolm Gladwel")
                .publisher("NXB Thế Giới")
                .numberOfPage(416)
                .published(2021)
                .status("Mở bán")
                .description("Cuốn sách Những kẻ xuất chúng sẽ giúp bạn tìm ra câu trả lời thông qua các phân tích về xã hội, văn hóa và thế hệ của những nhân vật kiệt xuất như Bill Gates, Beatles và Mozart, bên cạnh những thất bại đáng kinh ngạc của một số người khác (ví dụ: Christopher Langan, người có chỉ số IQ cao hơn Einstein nhưng rốt cuộc lại quay về làm việc trong một trại ngựa). Theo đó, cùng với tài năng và tham vọng, những người thành công đều được thừa hưởng một cơ hội đặt biệt để rèn luyện kỹ năng và cho phép họ vượt lên những người cùng trang lứa.\n" +
                        "\n" +
                        "Với giọng văn lôi cuốn và cách kể chuyện hết sức có duyên, Malcom Gladwell cũng viện dẫn rất nhiều giai thoại thú vị như tại sao phần lớn các cậu bé giỏi môn khúc côn cầu lại sinh vào tháng một, tại sao con cái của những người Do Thái nhập cư lại trở thành những luật sư quyền lực nhất New York, tại sao truyền thống văn hóa của nền nông nghiệp lúa nước lại có thể giúp trẻ em châu Á giỏi toán... Nhưng không chỉ có thế. Thông qua những ví dụ này, Gladwell muốn bàn luận về những con đường phức tạp dẫn đến thành công của con người.")
                .image("http://localhost:8080/api/FileUpload/files/nhung-ke-xuat-chung.jpg")
                .build();
        bookRepository.save(book13);
        Book book14 = Book.builder()
                .name("Bí Mật Tư Duy Triệu Phú")
                .genre("Kinh Tế")
                .stock(1523)
                .price(70200)
                .sales(1256)
                .author("T Harv Eker")
                .publisher("NXB Tổng Hợp TPHCM")
                .numberOfPage(287)
                .published(2021)
                .status("Mở bán")
                .description("Trong cuốn sách này T. Harv Eker sẽ tiết lộ những bí mật tại sao một số người lại đạt được những thành công vượt bậc, được số phận ban cho cuộc sống sung túc, giàu có, trong khi một số người khác phải chật vật, vất vả mới có một cuộc sống qua ngày. Bạn sẽ hiểu được nguồn gốc sự thật và những yếu tố quyết định thành công, thất bại để rồi áp dụng, thay đổi cách suy nghĩ, lên kế hoạch rồi tìm ra cách làm việc, đầu tư, sử dụng nguồn tài chính của bạn theo hướng hiệu quả nhất.\n" +
                        "\n" +
                        "Cuốn sách dành cho những ai còn loay hoay muốn tìm đường đến sự giàu có và thành công. “Bí mật tự duy triệu phú” mang đến nhiều tư duy mới cho người đọc về cách suy nghĩ của người giàu hay cách suy nghĩ để trở nên giàu có.\n" +
                        "\n" +
                        "Trong cuốn sách, Eker liệt kê 17 cách thức mà các kế hoạch chi tiết tài chính của những người giàu khác với người nghèo và tầng lớp trung lưu. Một chủ đề được xác định trong sách này là loại bỏ các suy nghĩ đổ lỗi cho sự thất bại. Eker lập luận rằng: Người giàu tin \"Tôi tạo ra cuộc sống của tôi\", còn những người nghèo cho rằng \"Cuộc sống sẽ tự nhiên đến với tôi\"; người giàu tập trung vào các cơ hội trong khi người nghèo tập trung vào những trở ngại; và những người giàu ngưỡng mộ những người giàu có và thành công khác trong khi người dân nghèo ghen tị, bực tức trước những người thành công và giàu có.")
                .image("http://localhost:8080/api/FileUpload/files/bi-mat-tu-duy-trieu-phu.jpg")
                .build();
        bookRepository.save(book14);
        Book book15 = Book.builder()
                .name("Người Bán Hàng Vĩ Đại Nhất Thế Giới")
                .genre("Kinh Tế")
                .stock(1789)
                .price(103600)
                .sales(256)
                .author("Og Mandino")
                .publisher("NXB Tổng Hợp TPHCM")
                .numberOfPage(296)
                .published(2020)
                .status("Mở bán")
                .description("\"Người Bán Hàng Vĩ Đại Nhất Thế Giới\" kể về câu chuyện của Hafid, một cậu bé chăn lạc đà nghèo, ở Jerusalem thời cổ đại.  Xuất thân là một cậu nhóc trông lạc đà cho đoàn buôn của Pathros, nhưng với quyết tâm đổi đời, muốn cải thiện vị trí của mình trong cuộc sống, Hafid luôn nuôi ước mơ được trở thành người bán hàng. Cậu tin chỉ như thế mình mới có cơ hội trở nên giàu có và thành công.\n" +
                        "\n" +
                        "Giống như nhiều người theo đuổi đam mê khác, trong suốt hành trình ấy đôi lúc Hafid đã nghĩ đến việc quay trở về với công việc chăn lạc đà, kiếm những đồng xu lẻ sống qua ngày. Biết bao nhiêu mối suy tư nghi ngờ, thương tiếc bản thân đã nảy ra trong tâm trí và bủa vây lấy cậu, nhưng với ý chí và quyết tâm, cuối cùng Hafid cũng trở thành người bán hàng tài năng nhất lúc bấy giờ.\n" +
                        "\n" +
                        "Bí mật nào đã làm nên thành công? Đó chính là những nguyên tắc được một thương nhân giàu có và thành công truyền lại và Hafid đã giữ kỹ trong 10 cuộn giấy da trong suốt hơn ba thập kỉ qua. Với Hafid, những thứ giản dị này còn quý hơn cả kim cương. Nhờ áp dụng những nguyên tắc trong cuộn giấy da đó mà Hafid được mệnh danh là “Người bán hàng vĩ đại nhất thế giới”. Tuy nhiên, sách không chỉ dạy bạn cách “bán hàng”, mà nó còn là một cuốn sách dạy bạn làm người, giúp bạn trở thành người “vĩ đại nhất” trong lĩnh vực mà bạn chọn.")
                .image("http://localhost:8080/api/FileUpload/files/nguoi-ban-hang-vi-dai-nhat-the-gioi.jpg")
                .build();
        bookRepository.save(book15);
        Book book16 = Book.builder()
                .name("Ông Già Và Biển Cả")
                .genre("Kinh Điển")
                .stock(2489)
                .price(31500)
                .sales(1245)
                .author("Ernest Hemingway")
                .publisher("NXB Văn Học")
                .numberOfPage(124)
                .published(2018)
                .status("Mở bán")
                .description("Ông già và Biển cả (tên tiếng Anh: The Old Man and the Sea) là một tiểu thuyết ngắn được Ernest Hemingway viết ở Cuba năm 1951 và xuất bản năm 1952. Nó là truyện ngắn dạng viễn tưởng cuối cùng được viết bởi Hemingway. Đây cũng là tác phẩm nổi tiếng và là một trong những đỉnh cao trong sự nghiệp sáng tác của nhà văn. Tác phẩm này đoạt giải Pulitzer cho tác phẩm hư cấu năm 1953. Nó cũng góp phần quan trọng để nhà văn được nhận Giải Nobel văn học năm 1954.\n" +
                        "\n" +
                        "Trong tác phẩm này, ông đã triệt để dùng nguyên lý mà ông gọi là “tảng băng trôi”, chỉ mô tả một phần nổi còn lại bảy phần chìm, khi mô tả sức mạnh của con cá, sự chênh lệch về lực lượng, về cuộc chiến đấu không cân sức giữa con cá hung dữ với ông già. Tác phẩm ca ngợi niềm tin, sức lao động và khát vọng của con người.")
                .image("http://localhost:8080/api/FileUpload/files/ong-gia-va-bien-ca.jpg")
                .build();
        bookRepository.save(book16);
        Book book17 = Book.builder()
                .name("Thần Thoại Hy Lạp")
                .genre("Kinh Điển")
                .stock(2862)
                .price(212400)
                .sales(1245)
                .author("Nhiều Tác Giả")
                .publisher("NXB Văn Học")
                .numberOfPage(856)
                .published(2023)
                .status("Mở bán")
                .description("Thần thoại Hy Lạp, một di sản văn hóa nhân loại, từ lâu đã trở thành nền tảng văn hóa không chỉ của một quốc gia mà còn là của cả một châu lục - Âu châu.\n" +
                        "\n" +
                        "Thần thoại Hy Lạp là những câu chuyện lý giải sự hình thành thế giới nhưng lấp lánh bên trong đó là khát khao, là ước mơ khẳng định tầm vóc của con người trước thiên nhiên. Những ước mơ ấy của loài người càng lấp lánh bao nhiêu thì bầu trời đêm thần thoại lại càng huyền bí, càng rộng mở bấy nhiêu.\n" +
                        "\n" +
                        "Đó chính là sức hấp dẫn ngàn đời của Thần thoại Hy Lạp.")
                .image("http://localhost:8080/api/FileUpload/files/than-thoai-hy-lap.jpg")
                .build();
        bookRepository.save(book17);
        Book book18 = Book.builder()
                .name("Không Gia Đình")
                .genre("Kinh Điển")
                .stock(2541)
                .price(126000)
                .sales(569)
                .author("Hector Malot")
                .publisher("NXB Văn Học")
                .numberOfPage(644)
                .published(2021)
                .status("Mở bán")
                .description("Câu chuyện về cậu bé bất hạnh Rémi lang bạt trên dặm trường thiên lý, dấn thân giữa tất cả những bần cùng đói khổ và những xa hoa lộng lẫy. Cậu thiếu niên nhỏ tuổi đã đi qua biết bao miền quê, thấy biết bao cảnh đời, mỗi bước chân đều in dấu ấn của những câu chuyện kỳ lạ, có lúc hoan hỉ mừng vui, có khi thê lương đau đớn nhưng luôn lấp lánh tình người. Cuộc hành trình của Rémi với đoàn xiếc khỉ, chó, với những người thợ mỏ, với cậu bé hát rong người Ý đưa người đọc trải nghiệm mọi cung bậc cảm xúc: thích thú, bất ngờ, hồi hộp, thương tâm, thậm chí cả tuyệt vọng và dạy cho ta - những người chưa, đang, hay đã trưởng thành - những bài học thấm thía về ý chí, nghị lực và lao động chân chính...\n" +
                        "\n" +
                        "Bàn về Không gia đình không cần bất cứ lời bình luận hoa mỹ nào khác, chỉ gói gọn trong hai từ: Kinh điển!")
                .image("http://localhost:8080/api/FileUpload/files/khong-gia-dinh.jpg")
                .build();
        bookRepository.save(book18);
        Book book19 = Book.builder()
                .name("Tiếng Gọi Nơi Hoang Dã")
                .genre("Kinh Điển")
                .stock(2756)
                .price(60000)
                .sales(789)
                .author("Jack London")
                .publisher("NXB Văn Học")
                .numberOfPage(254)
                .published(2023)
                .status("Mở bán")
                .description("Buck, đang sống trong một gia đình khá giả, bỗng bị bắt cóc và cuộc đời của Buck thay đổi từ đây. Chú bị bán làm chó kéo xe. Câu chuyện ghi lại Buck trên những bước đường khó nhọc, cùng với chủ, phải đối diện với cuộc đấu tranh sinh tồn. Chú đã học cách tồn tại và cuối cùng đã trở thành thủ lĩnh của đàn chó.\n" +
                        "\n" +
                        "Nhưng rồi, Buck đã nghe và bị thôi thúc bởi những tiếng gọi nơi rừng hoang. “Và có một thứ luôn gắn chặt với cảnh mộng về con người lông lá ấy là tiếng gọi, tiếng gọi cứ vang lên trong rừng thẳm. Mỗi lần nghe tiếng gọi ấy là lòng Buck tràn ngập nỗi xao xuyến bồi hồi và những ham muốn kỳ lạ. Nó mang đến cho Buck một niềm vui mơ hồ mà thú vị, và Buck nhận thấy trong lòng mình sôi lên cuồng nhiệt bao nỗi khát khao mong muốn những điều mà Buck không rõ là gì. Thỉnh thoảng Buck vùng dậy chạy vào rừng, đuổi theo tiếng gọi, sục tìm nó như thể nó là một vật có thể sờ mó được”…\n" +
                        "\n" +
                        "Sau một lần đi săn từ rừng trở về, Buck đã nhìn thấy cảnh hoang tàn, đẫm máu đối với người chủ nó thương yêu nhất: John cùng những người bạn và các chú chó kéo xe bị nhóm người Yhet tàn sát. Lúc này đây, tình yêu thương, trung thành mà Buck dành cho John đã trở thành nỗi đau thống thiết, khiến Buck trở nên hoang dã hơn bao giờ hết…")
                .image("http://localhost:8080/api/FileUpload/files/tieng-goi-noi-hoang-da.jpg")
                .build();
        bookRepository.save(book19);
        Book book20 = Book.builder()
                .name("Cuốn Theo Chiều Gió")
                .genre("Kinh Điển")
                .stock(2895)
                .price(187500)
                .sales(156)
                .author("Margaret Mitchell")
                .publisher("NXB Văn Học")
                .numberOfPage(920)
                .published(2021)
                .status("Mở bán")
                .description("Cuốn Theo Chiều Gió là cuốn tiểu thuyết duy nhất của nữ tác giả Margaret Mitchell, ngay từ khi mới ra đời, năm 1936, tác phẩm văn học này đã mau chóng chiếm được tình cảm của người dân Mỹ cũng như chinh phục trái tim của hàng triệu độc giả trên khắp thế giới\n" +
                        "\n" +
                        "Lấy bối cảnh từ cuộc nội chiến vô cùng khốc liệt giữa Bắc và Nam Mỹ, Cuốn Theo Chiều Gió với cốt truyện rõ ràng, logic, dễ hiểu, đã khắc họa một cách tài tình tâm trạng, tính cách và thân phận của nhiều lớp người trong chiến tranh và thời hậu chiến. Nhân vật chính của tiểu thuyết là cô gái Scarlett O'hara cùng với Rhett Butler trở thành cặp nhân vật điển hình, thuộc loại thành công nhất trong văn học Hoa Kỳ.\n" +
                        "\n" +
                        "Cuốn Theo Chiều Gió có sức hấp dẫn mãnh liệt giới trẻ Mỹ cũng như thanh niên toàn thế giới vì đây là cuốn tiểu thuyết tình yêu đặc sắc. Lạ kỳ thay, trong chiến tranh và những năm hậu chiến vô cùng gian khổ, tình yêu lại luôn luôn chói ngời, trở thành động lực giúp cho con người vượt qua chết chóc, đói khổ và sự hèn hạ... Không chỉ có tình yêu trai gái, Cuốn Theo Chiều Gió còn là bài ca của tình yêu quê hương đất nước, tình tương thân tương ái. Ba năm sau khi tiểu thuyết Cuốn Theo Chiều Gió ra đời, bộ phim cùng tên dựng theo tác phẩm của Margaret Mitchell được công chiếu đã trở thành sự kiện lớn, thành niềm tự hào của điện ảnh Mỹ.")
                .image("http://localhost:8080/api/FileUpload/files/cuon-theo-chieu-gio.jpg")
                .build();
        bookRepository.save(book20);
        Book book21 = Book.builder()
                .name("Thám Tử Lừng Danh Conan - Tập 1")
                .genre("Manga")
                .stock(3500)
                .price(24000)
                .sales(1425)
                .author("Gosho Aoyama")
                .publisher("NXB Văn Học")
                .numberOfPage(184)
                .published(2023)
                .status("Mở bán")
                .description("Thám Tử Lừng Danh Conan - Tập 1\n" +
                        "\n" +
                        "Kudo Shinichi là một cậu thám tử học sinh năng nổ với biệt tài suy luận có thể sánh ngang với Sherlock Holmes! Một ngày nọ, khi mải đuổi theo những kẻ khả nghi, cậu đã bị chúng cho uống một loại thuốc kì lạ khiến cho cơ thể bị teo nhỏ. Vậy là một thám tử tí hon xuất hiện với cái tên giả: Edogawa Conan!!\n" +
                        "\n" +
                        "Gosho Aoyama\n" +
                        "\n" +
                        "Xin chào, tôi là Aoyama đây!!\n" +
                        "\n" +
                        "Từ nhỏ tôi đã rất thích truyện trinh thám. Một khi vào hiệu sách, cứ thoáng thấy chữ “Holmes” hay tên một thám tử nổi tiếng nào đó là hai tay tự động vồ lấy. Vậy nên tôi đã cố vắt óc suy nghĩ để cho ra đời tác phẩm này. Liệu bạn có thể phá được vụ án trước Conan không nhỉ…")
                .image("http://localhost:8080/api/FileUpload/files/conan-tap-1.jpg")
                .build();
        bookRepository.save(book21);
        Book book22 = Book.builder()
                .name("Mỹ Vị Hầm Ngục - Tập 3")
                .genre("Manga")
                .stock(1257)
                .price(49300)
                .sales(452)
                .author("Kui Ryoko")
                .publisher("NXB Hồng Đức")
                .numberOfPage(192)
                .published(2022)
                .status("Mở bán")
                .description("Được tộc Orc chỉ dẫn, nhóm thám hiểm của Laios bắt đầu tìm kiếm địa đạo dẫn tới nơi Viêm long đang cư ngụ. Tuy nhiên, ở tầng 4 bao quanh là nước, chẳng ai biết rõ sinh vật nào đang ở bên dưới cái hồ sâu thẳm kia. Hãy cùng đón đọc tập 3 Mỹ vị hầm ngục để tham gia vào chuyến thám hiểm kì thú và ngon miệng.")
                .image("http://localhost:8080/api/FileUpload/files/my-vi-ham-nguc-tap-3.jpg")
                .build();
        bookRepository.save(book22);
        Book book23 = Book.builder()
                .name("OVERLORD - Tập 13")
                .genre("Manga")
                .stock(1526)
                .price(40800)
                .sales(548)
                .author("Maruyama Kugane, Miyama Hugin, Oshio Satoshi")
                .publisher("NXB Hồng Đức")
                .numberOfPage(250)
                .published(2022)
                .status("Mở bán")
                .description("Cuộc xung đột tại vương đô dần đi đến hồi kết, với đỉnh điểm là trận chiến giữa ác quỷ Jaldabaoth và Dark Hero Momon.\n" +
                        "\n" +
                        "Song song với những cuộc đối đầu nảy lửa, chân tướng của kế hoạch Gehenna cũng dần được hé lộ.\n" +
                        "\n" +
                        "Quay trở lại đại lăng mộ Nazarick sau những sự kiện vô cùng khốc liệt, cuộc sống thường ngày của Ainz và các thành viên Nazarick đã được khắc họa trọn vẹn qua một lăng kính đầy hài hước và nhẹ nhàng.")
                .image("http://localhost:8080/api/FileUpload/files/overlord-tap-13.jpg")
                .build();
        bookRepository.save(book23);
        Book book24 = Book.builder()
                .name("Thanh Gươm Diệt Quỷ - Kimetsu No Yaiba - Tập 23")
                .genre("Manga")
                .stock(2541)
                .price(24000)
                .sales(123)
                .author("Koyoharu Gotouge")
                .publisher("NXB Kim Đồng")
                .numberOfPage(232)
                .published(2021)
                .status("Mở bán")
                .description("Thanh Gươm Diệt Quỷ - Kimetsu No Yaiba - Tập 23: Vượt Qua Tháng Năm, Sinh Mệnh Tỏa Rạng\n" +
                        "\n" +
                        "Ấn phẩm độc đáo và đáng mong chờ nhất từ NXB Kim Đồng chính thức trình làng!!\n" +
                        "\n" +
                        "Trận chiến giữa nhóm Tanjiro và thủy tổ của loài quỷ - Kibutsuji Muzan đang dần ngã ngũ…!! Bốn loại thuốc của Tamayo đã làm Muzan suy yếu và dồn hắn vào đường cùng. Số phận của Tanjiro và Nezuko, cũng như toàn đội Diệt quỷ sẽ ra sao!? Trận chiến tưởng chừng vĩnh viễn cuối cùng cũng đi đến hồi kết!!")
                .image("http://localhost:8080/api/FileUpload/files/thanh-guom-diet-quy-tap-23.jpg")
                .build();
        bookRepository.save(book24);
        Book book25 = Book.builder()
                .name("Chú Thuật Hồi Chiến - Tập 1")
                .genre("Manga")
                .stock(1256)
                .price(28800)
                .sales(421)
                .author("Gege Akutami")
                .publisher("NXB Kim Đồng")
                .numberOfPage(192)
                .published(2022)
                .status("Mở bán")
                .description("Itadori Yuji là một học sinh cấp Ba sở hữu năng lực thể chất phi thường. Hằng ngày cậu thường tới bệnh viện chăm người ông đang ốm liệt giường. Nhưng một ngày nọ, phong ấn của “chú vật” vốn ngủ yên trong trường bị phá giải, quái vật xuất hiện. Để cứu hai anh chị khóa trên đang gặp nguy hiểm, Itadori đã xông vào trường và...\n" +
                        "\n" +
                        "Phần chính truyện của CHÚ THUẬT HỒI CHIẾN - Series bom tấn đã bán ra hơn 30 triệu bản tại Nhật năm 2021, bắt đầu…!!\n" +
                        "\n" +
                        "Tháng 3 này, JUJUTSU KAISEN - Tác phẩm Bom tấn hứa hẹn công phá nhiều bảng xếp hạng sách ở tất cả các quốc gia chính thức phát hành tại Việt Nam, với tập 1!\n" +
                        "\n" +
                        "Đặc biệt hơn, bộ truyện đã được NXB Kim Đồng công bố phát hành 2 ấn bản cùng ra song song cho tới khi kết thúc - Một điều chưa từng có trong tiền lệ. Sách cũng có một bước khởi đầu thành công với hàng vạn bản in được xuất bản để phục vụ bạn đọc! Chắc chắn trong tương lai, CHÚ THUẬT HỒI CHIẾN sẽ còn đạt nhiều thành tích cao hơn nữa!!")
                .image("http://localhost:8080/api/FileUpload/files/chu-thuat-hoi-chien-tap-1.jpg")
                .build();
        bookRepository.save(book25);
    }

    @Test
    public void initialOrders() throws BookNotFoundException {
        OrderRequest orderRequest1 = OrderRequest.builder()
                .customer(Customer.builder()
                        .name("Nguyễn Thanh Xuân")
                        .email("ntxuan@gmail.com")
                        .phone("0123456789")
                        .shippingAddress("Bến Tre")
                        .payment("Tiền Mặt")
                        .build())
                .orderDetailRequests(List.of(
                        new OrderDetailRequest(1,3),
                        new OrderDetailRequest(3,2)
                        ))
                .build();
        orderService.addNewOrder(orderRequest1);
        OrderRequest orderRequest2 = OrderRequest.builder()
                .customer(Customer.builder()
                        .name("Nguyễn Phi Hùng")
                        .email("nphung@gmail.com")
                        .phone("0123456789")
                        .shippingAddress("TPHCM")
                        .payment("Tiền Mặt")
                        .build())
                .orderDetailRequests(List.of(
                        new OrderDetailRequest(5,2),
                        new OrderDetailRequest(15,1),
                        new OrderDetailRequest(17,1)))
                .build();
        orderService.addNewOrder(orderRequest2);
        OrderRequest orderRequest3 = OrderRequest.builder()
                .customer(Customer.builder()
                        .name("Nguyễn Phương Lâm")
                        .email("nplam@gmail.com")
                        .phone("1234567890")
                        .shippingAddress("TPHCM")
                        .payment("Tiền Mặt")
                        .build())
                .orderDetailRequests(List.of(
                        new OrderDetailRequest(20,2),
                        new OrderDetailRequest(21,1)
                        ))
                .build();
        orderService.addNewOrder(orderRequest3);
        OrderRequest orderRequest4 = OrderRequest.builder()
                .customer(Customer.builder()
                        .name("Ngô Hồng Thái")
                        .email("nhthai@gmail.com")
                        .phone("0123456789")
                        .shippingAddress("TPHCM")
                        .payment("Tiền Mặt")
                        .build())
                .orderDetailRequests(List.of(
                        new OrderDetailRequest(24,1),
                        new OrderDetailRequest(14,1)
                ))
                .build();
        orderService.addNewOrder(orderRequest4);
        OrderRequest orderRequest5 = OrderRequest.builder()
                .customer(Customer.builder()
                        .name("Nguyễn Văn Thành")
                        .email("nvthanh@gmail.com")
                        .phone("0123456789")
                        .shippingAddress("TPHCM")
                        .payment("Tiền Mặt")
                        .build())
                .orderDetailRequests(List.of(
                        new OrderDetailRequest(8,1),
                        new OrderDetailRequest(5,1)
                ))
                .build();
        orderService.addNewOrder(orderRequest5);
    }
    @Test
    public void initialContacts(){
        Contact contact1 = Contact.builder()
                .name("Nguyễn Thanh Xuân")
                .email("ntxuan@gmail.com")
                .title("test1")
                .content("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.")
                .build();
        contactService.addNewContact(contact1);
        Contact contact2 = Contact.builder()
                .name("Nguyễn Phi Hùng")
                .email("nphung@gmail.com")
                .title("test2")
                .content("Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.")
                .build();
        contactService.addNewContact(contact2);
        Contact contact3 = Contact.builder()
                .name("Nguyễn Phương Lâm")
                .email("nplam@gmail.com")
                .title("test3")
                .content("Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.")
                .build();
        contactService.addNewContact(contact3);
        Contact contact4 = Contact.builder()
                .name("Ngô Hồng Thái")
                .email("nhthai@gmail.com")
                .title("test4")
                .content("A small river named Duden flows by their place and supplies it with the necessary regelialia.")
                .build();
        contactService.addNewContact(contact4);
        Contact contact5 = Contact.builder()
                .name("Nguyễn Văn Thành")
                .email("nvthanh@gmail.com")
                .title("test5")
                .content("It is a paradisematic country, in which roasted parts of sentences fly into your mouth.")
                .build();
        contactService.addNewContact(contact5);
    }
}
