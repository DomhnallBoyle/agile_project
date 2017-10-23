using CSC3045_CS2.Operations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;


namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for AnotherScreen.xaml
    /// </summary>
    public partial class AnotherScreen : Page
    {
        public AnotherScreen()
        {
            InitializeComponent();

           

            fuck.Content = Properties.Settings.Default.authToken.ToString();

            GetOperation getOperation = new GetOperation("http://localhost:8080", "/person");


        }

        public async void cunt_Click(object sender, RoutedEventArgs e)
        {
            GetOperation getOperation = new GetOperation("http://localhost:8080", "/users");
            HttpResponseMessage response = await getOperation.get();
            fuck.Content = response.Content.ReadAsStringAsync().Result;
        }

       

    }
}
